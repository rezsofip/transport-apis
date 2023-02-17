package com.example.transportapi.advice;

import com.example.transportapi.model.Transport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Transport> userNotFoundOrUnauthorized(ResponseStatusException ex) {
        if(ex.getMessage().contains("not authorized")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(Exception.class)
    public HttpStatus handleGeneralException(Exception ex) {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
