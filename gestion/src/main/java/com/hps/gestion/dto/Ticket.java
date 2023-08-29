package com.hps.gestion.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {
    private String id;
    private String key;
    private String summary;
    private String description;
    private String type;
}
