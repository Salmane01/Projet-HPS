package com.hps.gestion.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.hps.gestion.dto.Ticket;
import com.hps.gestion.shared.GlobalVariables;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.hps.gestion.service.RequestUtils.getRequestHeaders;

@Service
public class TicketService {

    private RestTemplate restTemplate = new RestTemplate();


    public List<Ticket> getTickets() {
        var request = getRequestHeaders(GlobalVariables.jiraUsername, GlobalVariables.jiraPassword);
        var response = restTemplate.exchange(GlobalVariables.jiraURL+"/rest/api/latest/search?jql=", HttpMethod.GET, request, JsonNode.class);
        JsonNode node = response.getBody().get("issues");
        List<Ticket> Ticket_List = new ArrayList<>();
        node.forEach(child ->
        {
            String id = child.get("id").asText();
            String key = child.get("key").asText();
            String summary = child.get("fields").get("summary").asText();
            String type = child.get("fields").get("issuetype").get("name").asText();
            String description = getDescription(child);
            Ticket_List.add(new Ticket(id, key, summary, description, type));
        });

        return Ticket_List;
    }

    private String getDescription(JsonNode node) {
        return node.get("fields").get("description").get("content").get(0).get("content").get(0).get("text").asText();
    }


}
