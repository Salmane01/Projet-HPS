package com.hps.gestion.controller;

import com.hps.gestion.dto.Ticket;
import com.hps.gestion.service.CommitService;
import com.hps.gestion.service.TicketService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
@RequestMapping("/commits")
public class CommitController {
    private final CommitService commitService;

    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }

    @GetMapping("/getAllCommits")
    public Object getCommits(@RequestParam String project,@RequestParam String repository , @RequestParam String branch) {
        return commitService.getCommits(project ,repository , branch);
     }

    /*@GetMapping("/getTaggedCommits")
    public Object getTaggedCommits(@RequestParam String repository) {
        return commitService.getTaggedCommits(repository);
    }
*/

    @GetMapping("/getCommitsByTagId")
    public Object getCommitByTagId(@RequestParam String project,@RequestParam String repository, @RequestParam String branch ,@RequestParam String tagId) {
        return commitService.getCommitByTagId(project , repository , branch , tagId);
    }

    @GetMapping("/getCommitsBetweenTags")
    public Object getCommitBetweenTags(@RequestParam String project,@RequestParam String repository ,@RequestParam String branch, @RequestParam String tag1 , @RequestParam String tag2) {
        return commitService.getCommitsBetweenTags(project ,repository  ,branch,tag1 , tag2);
    }

    @GetMapping("/getTicketsByCommitId")
    public Object getTicketsByCommitId(@RequestParam String commitMessage) {
        return commitService.getTicketsByCommitMessage(commitMessage);
    }

}
