package com.example.transportapi.model;

import com.example.transportapi.service.TransportService;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Transport {

    private int id;
    private String freight;
    private double weight;
    private String fromPlace;
    private String toPlace;
    private LocalDate dueDate;

    public Transport(String freight, double weight, String fromPlace, String toPlace, LocalDate dueDate) {
        this.id = TransportService.getLastId();
        TransportService.incrementLastId();
        this.freight = freight;
        this.weight = weight;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.dueDate = dueDate;
    }


}
