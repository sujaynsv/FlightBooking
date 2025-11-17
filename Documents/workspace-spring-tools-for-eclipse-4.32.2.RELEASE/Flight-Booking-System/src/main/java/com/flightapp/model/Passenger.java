package com.flightapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "passengers",
       indexes = {@Index(name = "idx_booking_id", columnList = "booking_id")})
public class Passenger {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    @JsonIgnore
    private Booking booking;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;
    
    @Column(name = "age", nullable = false)
    private Integer age;
    
    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "meal_preference")
    private MealPreference mealPreference = MealPreference.NONE;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    public enum Gender {
        MALE, FEMALE, OTHER
    }
    
    public enum MealPreference {
        VEG, NON_VEG, NONE
    }
    
    // Constructors
    public Passenger() {}
    
    public Passenger(Long id, Booking booking, String name, Gender gender, Integer age,
                     String seatNumber, MealPreference mealPreference, LocalDateTime createdAt) {
        this.id = id;
        this.booking = booking;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.seatNumber = seatNumber;
        this.mealPreference = mealPreference;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Booking getBooking() {
        return booking;
    }
    
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
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
    
    public MealPreference getMealPreference() {
        return mealPreference;
    }
    
    public void setMealPreference(MealPreference mealPreference) {
        this.mealPreference = mealPreference;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
