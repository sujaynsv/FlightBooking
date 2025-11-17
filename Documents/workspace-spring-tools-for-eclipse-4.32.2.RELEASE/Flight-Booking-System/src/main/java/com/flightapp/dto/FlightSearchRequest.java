package com.flightapp.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class FlightSearchRequest {
    
    @NotBlank(message = "From place is required")
    private String fromPlace;
    
    @NotBlank(message = "To place is required")
    private String toPlace;
    
    @NotNull(message = "Departure date is required")
    @Future(message = "Departure date must be in the future")
    private LocalDate departureDate;
    
    private String tripType;
    private LocalDate returnDate;
    
    public FlightSearchRequest() {}
    
    public FlightSearchRequest(String fromPlace, String toPlace, LocalDate departureDate, String tripType, LocalDate returnDate) {
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.departureDate = departureDate;
        this.tripType = tripType;
        this.returnDate = returnDate;
    }
    
    public String getFromPlace() {
        return fromPlace;
    }
    
    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }
    
    public String getToPlace() {
        return toPlace;
    }
    
    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }
    
    public LocalDate getDepartureDate() {
        return departureDate;
    }
    
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }
    
    public String getTripType() {
        return tripType;
    }
    
    public void setTripType(String tripType) {
        this.tripType = tripType;
    }
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
