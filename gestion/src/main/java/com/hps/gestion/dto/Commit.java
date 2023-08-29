package com.hps.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Commit {
    private String hash;
    private String message;
    private LocalDateTime date;
    private String author;
    private String repository;
    private String project;
    private String branch;
    private String jiraID;
    private String type;
    private String issueID;
    private String summary;
}
