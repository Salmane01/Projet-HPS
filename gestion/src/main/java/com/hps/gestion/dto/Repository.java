package com.hps.gestion.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Repository {
    private String name;
    private String project;
    //private List<Commit> commits;
}
