window.addEventListener('load', (event) => {
    AP.context.getContext(function(response){
        console.log("context", response);
        window.projectId = response.jira.project;
        getAssignableUsers(response.jira.project);

    });
});

function getAssignableUsers(projectId){
    AP.request('/rest/api/3/user/assignable/search?project=' + projectId.id, {
        success: function(responseText){
            let userSelect = document.getElementById('user-select');
            let users = JSON.parse(responseText);
            users.forEach(user => {
                userSelect.options[userSelect.options.length] = new Option(user.displayName, user.accountId);
            })

        },
        error: function(xhr, statusText, errorThrown){
            console.log(errorThrown);
        }
    });
}

function saveUser(){
    let userSelect = document.getElementById("user-select");
    let userName = userSelect.options[userSelect.selectedIndex].text;
    let userAtlassianId = userSelect.options[userSelect.selectedIndex].value;
    console.log("userName:" + userName + " userAtlassianId:" +userAtlassianId);

    let token = $('meta[name="token"]').attr('content');
    let headers = {'Authorization': "JWT " + token , 'Content-Type' : 'application/json'};
    let settings = { method: 'POST', headers: headers, body: JSON.stringify({'userName': userName, 'userAtlassianId': userAtlassianId, 'projectId': window.projectId})};
    let url = '/project/assign/user';
    fetch(url, settings)
        .then(response => response.json())
        .then((response) => {
            console.log("Save user:", response);
        }).catch(error => console.error('Error during save assigned user' , error));
}