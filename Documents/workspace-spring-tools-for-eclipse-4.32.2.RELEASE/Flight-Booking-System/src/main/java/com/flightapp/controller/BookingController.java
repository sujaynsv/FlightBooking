package com.flightapp.controller;

import com.flightapp.dto.*;
import com.flightapp.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @Valid @RequestBody BookingRequest request) {
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Booking created successfully", response));
    }
    
    @GetMapping("/{pnr}")
    public ResponseEntity<ApiResponse<BookingResponse>> getBooking(@PathVariable String pnr) {
        BookingResponse response = bookingService.getBookingByPNR(pnr);
        return ResponseEntity.ok(ApiResponse.success("Booking retrieved successfully", response));
    }
    
    @PutMapping("/{pnr}")
    public ResponseEntity<ApiResponse<BookingResponse>> updateBooking(
            @PathVariable String pnr,
            @Valid @RequestBody BookingUpdateRequest request) {
        BookingResponse response = bookingService.updateBooking(pnr, request);
        return ResponseEntity.ok(ApiResponse.success("Booking updated successfully", response));
    }
    
    @DeleteMapping("/{pnr}")
    public ResponseEntity<ApiResponse<String>> cancelBooking(@PathVariable String pnr) {
        bookingService.cancelBooking(pnr);
        return ResponseEntity.ok(ApiResponse.success("Booking cancelled successfully", pnr));
    }
}
