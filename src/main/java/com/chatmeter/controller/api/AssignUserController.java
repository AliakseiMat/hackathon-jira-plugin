package com.chatmeter.controller.api;

import com.atlassian.connect.spring.AtlassianHostUser;
import com.chatmeter.model.AssignedUser;
import com.chatmeter.service.AssignedUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class AssignUserController {

    @Autowired
    private AssignedUserService assignedUserService;

    @RequestMapping(value = "/project/assign/user", method = RequestMethod.POST)
    public AssignedUser getProjectPage(
            @AuthenticationPrincipal AtlassianHostUser hostUser,
            @RequestBody AssignedUser assignedUser
    ){

        assignedUser.setTenantId(hostUser.getHost().getClientKey());
        log.info("Save assigned user:{}", assignedUser);
        return assignedUserService.saveUser(assignedUser);
    }
}
