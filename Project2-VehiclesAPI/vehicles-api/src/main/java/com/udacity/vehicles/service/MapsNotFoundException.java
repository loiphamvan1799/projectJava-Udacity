package com.udacity.vehicles.service;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Price not found")
public class MapsNotFoundException extends RuntimeException {

    public MapsNotFoundException() {
    }

    public MapsNotFoundException(String message) {
        super(message);
    }
}
