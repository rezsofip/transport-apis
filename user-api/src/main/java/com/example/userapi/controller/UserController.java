package com.example.userapi.controller;

import com.example.userapi.model.User;
import com.example.userapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @GetMapping("/users")
    public ResponseEntity<User> getUser(@RequestParam String user) {
       Optional<User> foundUser = UserService.getUserByName(user);
       if(foundUser.isPresent()) {
           return ResponseEntity.ok(foundUser.get());
       }
       return ResponseEntity.notFound().build();

    }

    @PostMapping("/users")
    public ResponseEntity<User> postUser(@RequestBody User user) {
        Optional<User> foundUser = UserService.addUser(user);
        if(foundUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(foundUser.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @DeleteMapping("/users")
    public ResponseEntity<User> deleteUser(@RequestParam String user) {
        Optional<User> foundUser = UserService.deleteUser(user);
        if(foundUser.isPresent()) {
            return ResponseEntity.ok(foundUser.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Optional<User> foundUser = UserService.updateUser(user);
        if(foundUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(foundUser.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }



}
