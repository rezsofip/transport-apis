package com.example.userapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private Permission[] permissions;
    private LocalDateTime lastLogin;
    private boolean locked;
}
