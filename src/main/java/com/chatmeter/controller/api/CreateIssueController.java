package com.chatmeter.controller.api;

import com.atlassian.connect.spring.IgnoreJwt;
import com.chatmeter.model.AssignedUser;
import com.chatmeter.model.Event;
import com.chatmeter.service.AssignedUserService;
import com.chatmeter.service.JiraService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateIssueController {

    @Autowired
    private JiraService jiraService;
    @Autowired
    private AssignedUserService assignedUserService;

    @IgnoreJwt
    @RequestMapping(value = "/issue", method = RequestMethod.POST)
    public String getProjectPage(@RequestBody Event event) throws JsonProcessingException {


        AssignedUser assignedUser = assignedUserService.getAssignedUser();

        return jiraService.createIssue(assignedUser, event);
    }
}
