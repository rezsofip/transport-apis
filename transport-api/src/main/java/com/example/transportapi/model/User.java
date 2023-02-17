package com.example.transportapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;
    private Permission[] permissions;
    private LocalDateTime lastLogin;
    private boolean locked;


}
