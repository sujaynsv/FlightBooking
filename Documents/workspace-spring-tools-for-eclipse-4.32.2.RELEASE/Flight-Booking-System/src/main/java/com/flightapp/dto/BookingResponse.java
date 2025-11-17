package com.flightapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BookingResponse {
    
    private Long bookingId;
    private String pnr;
    private String flightNumber;
    private String fromPlace;
    private String toPlace;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String userName;
    private String userEmail;
    private Integer numberOfSeats;
    private BigDecimal totalPrice;
    private String bookingStatus;
    private LocalDateTime bookingDate;
    private List<PassengerInfo> passengers;
    
    public static class PassengerInfo {
        private String name;
        private String gender;
        private Integer age;
        private String seatNumber;
        private String mealPreference;
        
        public PassengerInfo() {}
        
        public PassengerInfo(String name, String gender, Integer age, String seatNumber, String mealPreference) {
            this.name = name;
            this.gender = gender;
            this.age = age;
            this.seatNumber = seatNumber;
            this.mealPreference = mealPreference;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getGender() {
            return gender;
        }
        
        public void setGender(String gender) {
            this.gender = gender;
        }
        
        public Integer getAge() {
            return age;
        }
        
        public void setAge(Integer age) {
            this.age = age;
        }
        
        public String getSeatNumber() {
            return seatNumber;
        }
        
        public void setSeatNumber(String seatNumber) {
            this.seatNumber = seatNumber;
        }
        
        public String getMealPreference() {
            return mealPreference;
        }
        
        public void setMealPreference(String mealPreference) {
            this.mealPreference = mealPreference;
        }
    }
    
    // Constructors
    public BookingResponse() {}
    
    public BookingResponse(Long bookingId, String pnr, String flightNumber, String fromPlace, String toPlace,
                          LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, String userName,
                          String userEmail, Integer numberOfSeats, BigDecimal totalPrice, String bookingStatus,
                          LocalDateTime bookingDate, List<PassengerInfo> passengers) {
        this.bookingId = bookingId;
        this.pnr = pnr;
        this.flightNumber = flightNumber;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.userName = userName;
        this.userEmail = userEmail;
        this.numberOfSeats = numberOfSeats;
        this.totalPrice = totalPrice;
        this.bookingStatus = bookingStatus;
        this.bookingDate = bookingDate;
        this.passengers = passengers;
    }
    
    // Getters and Setters
    public Long getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    
    public String getPnr() {
        return pnr;
    }
    
    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
    
    public String getFlightNumber() {
        return flightNumber;
    }
    
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
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
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }
    
    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getBookingStatus() {
        return bookingStatus;
    }
    
    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    
    public LocalDateTime getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public List<PassengerInfo> getPassengers() {
        return passengers;
    }
    
    public void setPassengers(List<PassengerInfo> passengers) {
        this.passengers = passengers;
    }
}
