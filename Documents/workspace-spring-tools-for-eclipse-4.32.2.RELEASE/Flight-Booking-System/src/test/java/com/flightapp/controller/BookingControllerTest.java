package com.flightapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightapp.dto.*;
import com.flightapp.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BookingService bookingService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private BookingRequest bookingRequest;
    private BookingResponse bookingResponse;
    
    @BeforeEach
    void setUp() {
        PassengerDTO passenger = new PassengerDTO();
        passenger.setName("John Doe");
        passenger.setGender("MALE");
        passenger.setAge(30);
        passenger.setSeatNumber("A1");
        passenger.setMealPreference("VEG");
        
        List<PassengerDTO> passengers = new ArrayList<>();
        passengers.add(passenger);
        
        bookingRequest = new BookingRequest();
        bookingRequest.setFlightId(1L);
        bookingRequest.setUserEmail("test@example.com");
        bookingRequest.setUserName("Test User");
        bookingRequest.setNumberOfSeats(1);
        bookingRequest.setPassengers(passengers);
        
        bookingResponse = new BookingResponse();
        bookingResponse.setBookingId(1L);
        bookingResponse.setPnr("AB25321XYZ");
        bookingResponse.setFlightNumber("TA1234");
        bookingResponse.setUserEmail("test@example.com");
        bookingResponse.setTotalPrice(new BigDecimal("5000.00"));
    }
    
    @Test
    void testCreateBooking_Success() throws Exception {
        when(bookingService.createBooking(any(BookingRequest.class))).thenReturn(bookingResponse);
        
        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.pnr").value("AB25321XYZ"));
    }
    
    @Test
    void testGetBooking_Success() throws Exception {
        when(bookingService.getBookingByPNR("AB25321XYZ")).thenReturn(bookingResponse);
        
        mockMvc.perform(get("/api/bookings/AB25321XYZ"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.pnr").value("AB25321XYZ"));
    }
    
    @Test
    void testCancelBooking_Success() throws Exception {
        doNothing().when(bookingService).cancelBooking("AB25321XYZ");
        
        mockMvc.perform(delete("/api/bookings/AB25321XYZ"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
