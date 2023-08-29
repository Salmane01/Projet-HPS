package com.hps.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Credentials {
    private String jiraURL;
    private String jiraUsername;
    private String jiraPassword;
    private String bitbucketURL;
    private String bitbucketUsername;
    private String bitbucketPassword;
}
