package com.hps.gestion.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.hps.gestion.dto.Commit;
import com.hps.gestion.dto.Repository;
import com.hps.gestion.shared.GlobalVariables;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.hps.gestion.service.RequestUtils.getRequestHeaders;

@Service
public class RepositoryService {
    private final CommitService commitService;
    private RestTemplate restTemplate = new RestTemplate();

    public RepositoryService(CommitService commitService){
        this.commitService = commitService;
    }

    public List<String> getAllRepositories(){
        var request = getRequestHeaders(GlobalVariables.bitbucketUsername, GlobalVariables.bitbucketPassword);
        var response = restTemplate.exchange(GlobalVariables.bitbucketURL+"/2.0/repositories/"+GlobalVariables.workspace, HttpMethod.GET, request, JsonNode.class);
        JsonNode node = response.getBody().get("values");
        List<String> repositoryList = new ArrayList<>();
        node.forEach(child ->
        {
            String name = child.get("name").asText();
            repositoryList.add(name);
        });
        return repositoryList;
    }

    public List<Repository> getRepositories(String project){
        var request = getRequestHeaders(GlobalVariables.bitbucketUsername, GlobalVariables.bitbucketPassword);
        var response = restTemplate.exchange(GlobalVariables.bitbucketURL+"/2.0/repositories/"+GlobalVariables.workspace+"?q=project.name=\""+project+"\"", HttpMethod.GET, request, JsonNode.class);
        JsonNode node = response.getBody().get("values");
        List<Repository> repositoryList = new ArrayList<>();
        node.forEach(child ->
        {
            String name = child.get("name").asText();
            repositoryList.add(new Repository(name , project));
        });
        return repositoryList;
    }

    public List<String> getTags(String project ,String repository , String branch){
        var request = getRequestHeaders(GlobalVariables.bitbucketUsername, GlobalVariables.bitbucketPassword);
        var response = restTemplate.exchange(GlobalVariables.bitbucketURL+"/2.0/repositories/"+GlobalVariables.workspace+"/"+repository+"/refs/tags?q=", HttpMethod.GET, request, JsonNode.class);
        JsonNode node = response.getBody().get("values");
        List<String> tagList = new ArrayList<>();
        List<Commit> listCommit = commitService.getCommits(project ,repository , branch);
        List<String> listCommitHash = new ArrayList<>();
        listCommit.forEach(commit -> listCommitHash.add(commit.getHash()));
        node.forEach(child ->
        {
            if(listCommitHash.contains(child.get("target").get("hash").asText())){
                String name = child.get("name").asText();
                tagList.add(name);
            }
        });
        return tagList;
    }

    public List<String> getBranches(String repository){
        var request = getRequestHeaders(GlobalVariables.bitbucketUsername, GlobalVariables.bitbucketPassword);
        var response = restTemplate.exchange(GlobalVariables.bitbucketURL+"/2.0/repositories/"+GlobalVariables.workspace+"/"+repository+"/refs/branches/", HttpMethod.GET, request, JsonNode.class);
        JsonNode node = response.getBody().get("values");
        List<String> branchList = new ArrayList<>();
        node.forEach(child ->
        {
            String name = child.get("name").asText();
            //List<Commit> commitList = commitService.getCommits(name);
            branchList.add(name);
        });
        return branchList;
    }

    public List<String> getProjects(){
        var request = getRequestHeaders(GlobalVariables.bitbucketUsername, GlobalVariables.bitbucketPassword);
        var response = restTemplate.exchange(GlobalVariables.bitbucketURL+"/2.0/workspaces/"+GlobalVariables.workspace+"/projects", HttpMethod.GET, request, JsonNode.class);
        JsonNode node = response.getBody().get("values");
        List<String> projectList = new ArrayList<>();
        node.forEach(child ->
        {
            String name = child.get("name").asText();
            projectList.add(name);
        });
        return projectList;
    }



    public Set<Commit> getCommitsByTicketIds(List<String> ticketIds){
        List<String> listProjects = this.getProjects();
        List<Repository> listRepositories = new ArrayList<>();
        listProjects.forEach(project -> {
            getRepositories(project).forEach(repository -> {
                listRepositories.add(repository);
            });
        });
        Set<Commit> setCommits = new HashSet<>();
        Set<Commit> res = new HashSet<>();
        List<String> listBranches;
        for(Repository repo : listRepositories) {
            listBranches = this.getBranches(repo.getName());
            for(String branch: listBranches) {
                setCommits.addAll(this.commitService.getCommits(repo.getProject(), repo.getName(), branch));
            }
        }

        for(String ticketId: ticketIds) {
            for(Commit commit: setCommits) {
                if(commit.getMessage().contains(ticketId)) {
                    res.add(commit);
                }
            }
        }
        return res;
    }



}
