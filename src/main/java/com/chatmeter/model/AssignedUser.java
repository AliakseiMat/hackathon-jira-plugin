package com.chatmeter.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AssignedUser {

    private String userName;
    private String userAtlassianId;
    private JiraProjectId projectId;
    private String tenantId;
}
