package com.hps.gestion.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.hps.gestion.dto.Commit;
import com.hps.gestion.dto.Tag;
import com.hps.gestion.dto.Ticket;
import com.hps.gestion.shared.GlobalVariables;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.hps.gestion.service.RequestUtils.getRequestHeaders;

@Service
public class CommitService {

    private final TicketService ticketService;
    private RestTemplate restTemplate = new RestTemplate();

    public CommitService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public List<Commit> getCommits(String project,String repository , String branch) {
        var request = getRequestHeaders(GlobalVariables.bitbucketUsername, GlobalVariables.bitbucketPassword);
        var response = restTemplate.exchange(GlobalVariables.bitbucketURL+"/2.0/repositories/"+GlobalVariables.workspace+"/"+repository+"/commits/" + branch, HttpMethod.GET, request, JsonNode.class);
        JsonNode node = response.getBody().get("values");
        List<Commit> commitList = new ArrayList<>();
        node.forEach(child ->
        {
            String hash = child.get("hash").asText();
            String message = child.get("message").asText();
            String author = child.get("author").get("user").get("display_name").asText();
            String date = child.get("date").asText();
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(date);
            LocalDateTime localDateTime = offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            List<Ticket> tickets = getTicketsByCommitMessage(message);
            String jiraID = "";
            String type = "";
            String issueID = "";
            String summary = "";
            if (tickets.size() == 0){
                jiraID = "";
                type = "";
                issueID = "";
                summary = "";
            }else{
                jiraID = tickets.get(0).getKey();
                type = tickets.get(0).getType();
                issueID = tickets.get(0).getId();
                summary = tickets.get(0).getSummary();
            }
            commitList.add(new Commit(hash, message, localDateTime , author ,repository , project, branch , jiraID , type , issueID , summary ));
        });
        return commitList;

    }

    public List<Ticket> getTicketsByCommitMessage(String commitMessage){
        List<Ticket> ticketList = ticketService.getTickets();
        return ticketList.stream().filter(ticket -> commitMessage.contains(ticket.getKey())).toList();
    }

    public List<Commit> getCommitByTagId(String project,String repository ,String branch, String tagId){
        var request = getRequestHeaders(GlobalVariables.bitbucketUsername, GlobalVariables.bitbucketPassword);
        var response = restTemplate.exchange(GlobalVariables.bitbucketURL+"/2.0/repositories/"+GlobalVariables.workspace+"/"+repository+"/commits/" + tagId, HttpMethod.GET, request, JsonNode.class);
        JsonNode node = response.getBody().get("values");
        List<Commit> commitList = new ArrayList<>();
        node.forEach(child ->
        {
            String hash = child.get("hash").asText();
            String message = child.get("message").asText();
            String author = child.get("author").get("user").get("display_name").asText();
            String date = child.get("date").asText();
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(date);
            LocalDateTime localDateTime = offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            List<Ticket> tickets = getTicketsByCommitMessage(message);
            String jiraID = "";
            String type = "";
            String issueID = "";
            String parentIssue = "";
            String summary = "";
            if (tickets.size() == 0){
                jiraID = "";
                type = "";
                issueID = "";
                parentIssue = "";
                summary = "";
            }else{
                jiraID = tickets.get(0).getKey();
                type = tickets.get(0).getType();
                issueID = tickets.get(0).getId();
                summary = tickets.get(0).getSummary();
            }
            commitList.add(new Commit(hash, message, localDateTime , author ,repository,project ,branch, jiraID , type , issueID , summary ));
        });
        return commitList;

    }

    public List<Commit> getCommitsBetweenTags(String project ,String repository , String branch ,String tagA ,String tagB){
        // remember tagA >> tagB
        List<Commit> listA = getCommitByTagId(project , repository , branch ,tagA);
        List<Commit> listB = getCommitByTagId(project , repository ,branch, tagB);
        if (listA.size()>listB.size()){
            listA.removeAll(listB);
            return listA;
        }else{
            listB.removeAll(listA);
            return listB;
        }
    }

}
