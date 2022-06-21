package com.example.server.model.dto.manager;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private Long id;
    private String name;
    private String location;
    private Date start_time;
    private Date end_time;
    
}
