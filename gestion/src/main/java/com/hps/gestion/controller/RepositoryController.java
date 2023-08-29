package com.hps.gestion.controller;

import com.hps.gestion.service.RepositoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repository")
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService){
        this.repositoryService = repositoryService;
    }

    @GetMapping("/getRepositories")
    public Object getRepositories(@RequestParam String project){
        return repositoryService.getRepositories(project);
    }

    @GetMapping("/getTags")
    public Object getTags(@RequestParam String project,@RequestParam String repository , @RequestParam String branch){
        return repositoryService.getTags(project ,repository , branch);
    }

    @GetMapping("/getBranches")
    public Object getBranches(@RequestParam String repository) {return repositoryService.getBranches(repository);}

    @GetMapping("/getProjects")
    public Object getProjects(){return repositoryService.getProjects();}

    @PostMapping("/getCommitsByTicketIds")
    public Object getCommitsByTicketId(@RequestBody List<String> ticketIds) {
        return repositoryService.getCommitsByTicketIds(ticketIds);
    }

}
