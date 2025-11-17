package com.flightapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class BookingUpdateRequest {
    
    @NotEmpty(message = "Passenger details are required")
    @Size(min = 1, message = "At least one passenger is required")
    @Valid
    private List<PassengerDTO> passengers;
    
    public BookingUpdateRequest() {}
    
    public BookingUpdateRequest(List<PassengerDTO> passengers) {
        this.passengers = passengers;
    }
    
    public List<PassengerDTO> getPassengers() {
        return passengers;
    }
    
    public void setPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
    }
}
