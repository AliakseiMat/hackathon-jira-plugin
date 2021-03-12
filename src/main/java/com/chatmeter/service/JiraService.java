package com.chatmeter.service;

import com.atlassian.connect.spring.AtlassianHostRestClients;
import com.chatmeter.model.AssignedUser;
import com.chatmeter.model.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Log4j2
@Service
public class JiraService {

    @Autowired
    private AtlassianHostRestClients atlassianHostRestClients;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String createIssue(
            AssignedUser assignedUser,
            Event event
    ) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectNode issue = objectMapper.createObjectNode();
        ObjectNode fields = objectMapper.createObjectNode();
        fields.put("summary", event.getLocation());

        ObjectNode issuetype = objectMapper.createObjectNode();
        issuetype.put("id", "10002");
        fields.put("issuetype", issuetype);

        ObjectNode project = objectMapper.createObjectNode();
        project.put("id", assignedUser.getProjectId().getId());
        fields.put("project", project);

        ArrayNode labels = objectMapper.createArrayNode();
        labels
                .add(event.getSource())
                .add("Rating:" + event.getRating().toString())
                .add(event.getType());
        fields.put("labels", labels);


        fields.put("description", createDescription(event));

    //    ObjectNode reporter = objectMapper.createObjectNode();
    //    reporter.put("id", reporterId);
    //    fields.put("reporter", reporter);

        ObjectNode assignee = objectMapper.createObjectNode();
        assignee.put("id", assignedUser.getUserAtlassianId());
        fields.put("assignee", assignee);

        issue.put("fields", fields);
        log.info("Issue:{}", issue.toString());

        HttpEntity<String> request = new HttpEntity<String>(issue.toString(), headers);
        String url = "https://chatmeter-addon-hackathon.atlassian.net/rest/api/3/issue";
        return atlassianHostRestClients.authenticatedAsAddon().postForObject(url, request, String.class);
    }

    private ObjectNode createDescription(Event event){

        StringBuffer sb = new StringBuffer();
        sb.append("Source:" + event.getSource() + "\n");
        sb.append("Type:" + event.getType() + "\n");
        sb.append("Rating:" + event.getRating() + "\n");
        sb.append("Review:" + event.getText() + "\n");

        ArrayNode contentArray = objectMapper.createArrayNode();

        ObjectNode innerContent = objectMapper.createObjectNode();
        innerContent
                .put("text", sb.toString())
                .put("type", "text");

        ArrayNode innerContentArray = objectMapper.createArrayNode();
        innerContentArray.add(innerContent);
        ObjectNode content = objectMapper.createObjectNode();
        content
                .put("type", "paragraph")
                .put("content", innerContentArray);
        contentArray.add(content);

        ObjectNode issueDescription = objectMapper.createObjectNode();
        issueDescription
                .put("type", "doc")
                .put("version", 1)
                .put("content", contentArray);

        log.info("Description created:{}", issueDescription.toString());

        return issueDescription;
    }
}
