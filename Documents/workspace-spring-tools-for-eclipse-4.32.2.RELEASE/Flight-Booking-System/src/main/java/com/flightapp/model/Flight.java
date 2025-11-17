package com.flightapp.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights",
       indexes = {
           @Index(name = "idx_flight_search", columnList = "from_place, to_place, departure_date_time"),
           @Index(name = "idx_departure_date", columnList = "departure_date_time")
       })
public class Flight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "flight_number", nullable = false, unique = true, length = 20)
    private String flightNumber;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;
    
    @Column(name = "from_place", nullable = false, length = 100)
    private String fromPlace;
    
    @Column(name = "to_place", nullable = false, length = 100)
    private String toPlace;
    
    @Column(name = "departure_date_time", nullable = false)
    private LocalDateTime departureDateTime;
    
    @Column(name = "arrival_date_time", nullable = false)
    private LocalDateTime arrivalDateTime;
    
    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;
    
    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;
    
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "trip_type")
    private TripType tripType = TripType.ONE_WAY;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FlightStatus status = FlightStatus.SCHEDULED;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum TripType {
        ONE_WAY, ROUND_TRIP
    }
    
    public enum FlightStatus {
        SCHEDULED, DELAYED, CANCELLED, COMPLETED
    }
    
    // Constructors
    public Flight() {}
    
    public Flight(Long id, String flightNumber, Airline airline, String fromPlace, String toPlace,
                  LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, Integer totalSeats,
                  Integer availableSeats, BigDecimal price, TripType tripType, FlightStatus status,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.price = price;
        this.tripType = tripType;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
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
    
    public Airline getAirline() {
        return airline;
    }
    
    public void setAirline(Airline airline) {
        this.airline = airline;
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
    
    public TripType getTripType() {
        return tripType;
    }
    
    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }
    
    public FlightStatus getStatus() {
        return status;
    }
    
    public void setStatus(FlightStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
