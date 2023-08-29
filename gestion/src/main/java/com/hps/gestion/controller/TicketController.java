package com.hps.gestion.controller;


import com.hps.gestion.service.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/get")
    public Object getTickets() { return ticketService.getTickets();}



    /*@GetMapping("/getTicketsBetweenTags")
    public Object getTicketsBetweenTags(@RequestParam String tag1 , @RequestParam String tag2) {
        return ticketService.getTicketsBetweenTags(tag1 , tag2);
    }*/

}
