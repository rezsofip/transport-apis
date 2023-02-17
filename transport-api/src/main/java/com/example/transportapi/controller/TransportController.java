package com.example.transportapi.controller;

import com.example.transportapi.model.Transport;
import com.example.transportapi.service.TransportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class TransportController {

    @GetMapping("/transportdata")
    public List<Transport> getTransportData(@RequestParam String user) throws ResponseStatusException {
        return TransportService.getTransportData(user);
    }

    @GetMapping("/transportdata/{id}")
    public ResponseEntity<Transport> getTransportDataById(@RequestParam String user, @PathVariable int id) {
        Optional<Transport> foundTransport = TransportService.getTransportData(user, id);
        if(foundTransport.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(foundTransport.get());
    }

    @PostMapping("/transportdata")
    public ResponseEntity<Transport> addTransportData(@RequestParam String user, @RequestBody Transport transport) {
        return ResponseEntity.status(HttpStatus.CREATED).body(TransportService.addTransportData(user, transport));
    }

    @DeleteMapping("/transportdata/{id}")
    public ResponseEntity<Transport> deleteTransportData(@RequestParam String user, @PathVariable int id) {
        Optional<Transport> foundTransport = TransportService.removeTransportData(user, id);
        if(foundTransport.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(foundTransport.get());
    }

    @PutMapping("/transportdata")
    public ResponseEntity<Transport> modifyTransportData(@RequestParam String user, @RequestBody Transport transport) {
        Transport foundTransport = TransportService.editTransportData(user, transport);
        return  foundTransport != null ? ResponseEntity.ok(foundTransport) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
