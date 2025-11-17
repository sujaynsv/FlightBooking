package com.flightapp.controller;

import com.flightapp.dto.*;
import com.flightapp.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    
    @Autowired
    private FlightService flightService;
    
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<FlightSearchResponse>>> searchFlights(
            @Valid @RequestBody FlightSearchRequest request) {
        List<FlightSearchResponse> flights = flightService.searchFlights(request);
        return ResponseEntity.ok(ApiResponse.success("Flights retrieved successfully", flights));
    }
    
    @PostMapping("/inventory")
    public ResponseEntity<ApiResponse<String>> addFlight(@Valid @RequestBody InventoryRequest request) {
        flightService.addFlight(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Flight added successfully", request.getFlightNumber()));
    }
}
