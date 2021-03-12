package com.chatmeter.service;

import com.chatmeter.model.AssignedUser;
import org.springframework.stereotype.Service;

@Service
public class AssignedUserService {

    private AssignedUser assignedUser;

    public AssignedUser saveUser(AssignedUser assignedUser){

        this.assignedUser = assignedUser;

        return assignedUser;
    }

    public AssignedUser getAssignedUser(){

        return this.assignedUser;
    }
}
