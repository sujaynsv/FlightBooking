package com.flightapp.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.flightapp.validation.ValidDateRange;

@ValidDateRange
public class InventoryRequest {
    
    @NotBlank(message = "Flight number is required")
    @Pattern(regexp = "^[A-Z]{2}\\d{3,4}$", message = "Flight number format: AB1234")
    private String flightNumber;
    
    @NotNull(message = "Airline ID is required")
    private Long airlineId;
    
    @NotBlank(message = "From place is required")
    private String fromPlace;
    
    @NotBlank(message = "To place is required")
    private String toPlace;
    
    @NotNull(message = "Departure date time is required")
    @Future(message = "Departure must be in the future")
    private LocalDateTime departureDateTime;
    
    @NotNull(message = "Arrival date time is required")
    private LocalDateTime arrivalDateTime;
    
    @NotNull(message = "Total seats is required")
    @Min(value = 1, message = "Total seats must be at least 1")
    @Max(value = 500, message = "Total seats cannot exceed 500")
    private Integer totalSeats;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;
    
    @Pattern(regexp = "ONE_WAY|ROUND_TRIP", message = "Trip type must be ONE_WAY or ROUND_TRIP")
    private String tripType = "ONE_WAY";
    
    // Constructors
    public InventoryRequest() {}
    
    public InventoryRequest(String flightNumber, Long airlineId, String fromPlace, String toPlace,
                           LocalDateTime departureDateTime, LocalDateTime arrivalDateTime,
                           Integer totalSeats, BigDecimal price, String tripType) {
        this.flightNumber = flightNumber;
        this.airlineId = airlineId;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.totalSeats = totalSeats;
        this.price = price;
        this.tripType = tripType;
    }
    
    // Getters and Setters
    public String getFlightNumber() {
        return flightNumber;
    }
    
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    public Long getAirlineId() {
        return airlineId;
    }
    
    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
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
    
    public Integer getTotalSeats() {
        return totalSeats;
    }
    
    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
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
}
