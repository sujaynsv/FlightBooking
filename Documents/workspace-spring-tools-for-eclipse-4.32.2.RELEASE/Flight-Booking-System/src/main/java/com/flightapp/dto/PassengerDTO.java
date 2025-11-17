package com.flightapp.dto;

import jakarta.validation.constraints.*;

public class PassengerDTO {
    
    @NotBlank(message = "Passenger name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "MALE|FEMALE|OTHER", message = "Gender must be MALE, FEMALE, or OTHER")
    private String gender;
    
    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 120, message = "Age must not exceed 120")
    private Integer age;
    
    @NotBlank(message = "Seat number is required")
    @Pattern(regexp = "^[A-Z]\\d{1,2}$", message = "Seat number format: A1, B12, etc.")
    private String seatNumber;
    
    @Pattern(regexp = "VEG|NON_VEG|NONE", message = "Meal preference must be VEG, NON_VEG, or NONE")
    private String mealPreference = "NONE";
    
    public PassengerDTO() {}
    
    public PassengerDTO(String name, String gender, Integer age, String seatNumber, String mealPreference) {
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
