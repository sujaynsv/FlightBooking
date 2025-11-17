package com.flightapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightapp.dto.*;
import com.flightapp.model.Flight;
import com.flightapp.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
class FlightControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private FlightService flightService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private FlightSearchRequest searchRequest;
    private List<FlightSearchResponse> searchResponses;
    
    @BeforeEach
    void setUp() {
        searchRequest = new FlightSearchRequest();
        searchRequest.setFromPlace("Delhi");
        searchRequest.setToPlace("Mumbai");
        searchRequest.setDepartureDate(LocalDate.now().plusDays(1));
        
        FlightSearchResponse response = new FlightSearchResponse();
        response.setId(1L);
        response.setFlightNumber("TA1234");
        response.setFromPlace("Delhi");
        response.setToPlace("Mumbai");
        response.setPrice(new BigDecimal("5000.00"));
        
        searchResponses = new ArrayList<>();
        searchResponses.add(response);
    }
    
    @Test
    void testSearchFlights_Success() throws Exception {
        when(flightService.searchFlights(any(FlightSearchRequest.class))).thenReturn(searchResponses);
        
        mockMvc.perform(post("/api/flights/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].flightNumber").value("TA1234"));
    }
}
