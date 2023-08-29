package com.hps.gestion.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.hps.gestion.dto.Credentials;
import com.hps.gestion.shared.GlobalVariables;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.hps.gestion.service.RequestUtils.getRequestHeaders;

@Service
public class LoginService {

    public Credentials setCredentials(Credentials credentials){
        GlobalVariables.jiraURL = credentials.getJiraURL();
        GlobalVariables.jiraUsername = credentials.getJiraUsername();
        GlobalVariables.jiraPassword = credentials.getJiraPassword();
        GlobalVariables.bitbucketURL = credentials.getBitbucketURL();
        GlobalVariables.bitbucketUsername = credentials.getBitbucketUsername();
        GlobalVariables.bitbucketPassword = credentials.getBitbucketPassword();
        GlobalVariables.workspace = getWorkspace();
        return credentials;

    }
    public String getWorkspace(){
        var request = getRequestHeaders(GlobalVariables.bitbucketUsername, GlobalVariables.bitbucketPassword);
        var response = new RestTemplate().exchange(GlobalVariables.bitbucketURL+"/2.0/user/permissions/workspaces", HttpMethod.GET, request, JsonNode.class);
        JsonNode node = response.getBody().get("values");
        String result = node.get(0).get("workspace").get("slug").asText();
        return result;
    }
}
