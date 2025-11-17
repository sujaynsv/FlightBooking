package com.flightapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FlightSearchResponse {
    
    private Long id;
    private String flightNumber;
    private String airlineName;
    private String airlineCode;
    private String fromPlace;
    private String toPlace;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private Integer availableSeats;
    private BigDecimal price;
    private String tripType;
    private String status;
    private String duration;
    
    public FlightSearchResponse() {}
    
    public FlightSearchResponse(Long id, String flightNumber, String airlineName, String airlineCode,
                                String fromPlace, String toPlace, LocalDateTime departureDateTime,
                                LocalDateTime arrivalDateTime, Integer availableSeats, BigDecimal price,
                                String tripType, String status, String duration) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.airlineName = airlineName;
        this.airlineCode = airlineCode;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.availableSeats = availableSeats;
        this.price = price;
        this.tripType = tripType;
        this.status = status;
        this.duration = duration;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFlightNumber() {
        return flightNumber;
    }
    
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    public String getAirlineName() {
        return airlineName;
    }
    
    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
    
    public String getAirlineCode() {
        return airlineCode;
    }
    
    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
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
    
    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }
    
    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }
    
    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }
    
    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }
    
    public Integer getAvailableSeats() {
        return availableSeats;
    }
    
    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getTripType() {
        return tripType;
    }
    
    public void setTripType(String tripType) {
        this.tripType = tripType;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public void setDuration(String duration) {
        this.duration = duration;
    }
}
