package com.hps.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tag {
    private String id;
    private String message;
    private LocalDateTime date;
    private List<Commit> commits;
}
