package com.chatmeter.controller.web;

import com.atlassian.connect.spring.AtlassianHostUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectPageController {

    @RequestMapping(value = "/project/page", method = RequestMethod.GET)
    public ModelAndView getProjectPage(
            @AuthenticationPrincipal AtlassianHostUser hostUser,
            String projectId
    ){

        ModelAndView model = new ModelAndView();
        model.addObject("projectId", projectId);
        model.setViewName("project-page-view");

        return model;
    }
}
