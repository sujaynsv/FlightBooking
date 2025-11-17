package com.flightapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public class BookingRequest {
    
    @NotNull(message = "Flight ID is required")
    private Long flightId;
    
    @NotBlank(message = "User email is required")
    @Email(message = "Invalid email format")
    private String userEmail;
    
    @NotBlank(message = "User name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String userName;
    
    @NotNull(message = "Number of seats is required")
    @Min(value = 1, message = "At least 1 seat must be booked")
    @Max(value = 9, message = "Maximum 9 seats can be booked at once")
    private Integer numberOfSeats;
    
    @NotEmpty(message = "Passenger details are required")
    @Size(min = 1, message = "At least one passenger is required")
    @Valid
    private List<PassengerDTO> passengers;
    
    public BookingRequest() {}
    
    public BookingRequest(Long flightId, String userEmail, String userName, Integer numberOfSeats, List<PassengerDTO> passengers) {
        this.flightId = flightId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.numberOfSeats = numberOfSeats;
        this.passengers = passengers;
    }
    
    public Long getFlightId() {
        return flightId;
    }
    
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }
    
    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    
    public List<PassengerDTO> getPassengers() {
        return passengers;
    }
    
    public void setPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
    }
}
