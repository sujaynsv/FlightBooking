package com.flightapp.service;

import com.flightapp.dto.*;
import com.flightapp.exception.InsufficientSeatsException;
import com.flightapp.exception.InvalidBookingException;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.*;
import com.flightapp.repository.*;
import com.flightapp.util.PNRGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private PassengerRepository passengerRepository;
    
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        // Validate flight and seats
        Flight flight = flightRepository.findById(request.getFlightId())
            .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + request.getFlightId()));
        
        if (flight.getAvailableSeats() < request.getNumberOfSeats()) {
            throw new InsufficientSeatsException("Insufficient seats. Requested: " + 
                request.getNumberOfSeats() + ", Available: " + flight.getAvailableSeats());
        }
        
        // Create booking
        Booking booking = new Booking();
        booking.setPnr(PNRGenerator.generatePNR());
        booking.setFlight(flight);
        booking.setUserEmail(request.getUserEmail());
        booking.setUserName(request.getUserName());
        booking.setNumberOfSeats(request.getNumberOfSeats());
        booking.setTotalPrice(flight.getPrice().multiply(new BigDecimal(request.getNumberOfSeats())));
        booking.setBookingStatus(Booking.BookingStatus.CONFIRMED);
        booking.setBookingDate(LocalDateTime.now());
        
        // Add passengers
        for (PassengerDTO passengerDTO : request.getPassengers()) {
            Passenger passenger = new Passenger();
            passenger.setName(passengerDTO.getName());
            passenger.setGender(Passenger.Gender.valueOf(passengerDTO.getGender()));
            passenger.setAge(passengerDTO.getAge());
            passenger.setSeatNumber(passengerDTO.getSeatNumber());
            passenger.setMealPreference(Passenger.MealPreference.valueOf(passengerDTO.getMealPreference()));
            booking.addPassenger(passenger);
        }
        
        // Update flight seats
        flight.setAvailableSeats(flight.getAvailableSeats() - request.getNumberOfSeats());
        flightRepository.save(flight);
        
        booking = bookingRepository.save(booking);
        
        return mapToBookingResponse(booking);
    }
    
    public BookingResponse getBookingByPNR(String pnr) {
        Booking booking = bookingRepository.findByPnr(pnr)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with PNR: " + pnr));
        return mapToBookingResponse(booking);
    }
    
    @Transactional
    public void cancelBooking(String pnr) {
        Booking booking = bookingRepository.findByPnr(pnr)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with PNR: " + pnr));
        
        if (booking.getBookingStatus() == Booking.BookingStatus.CANCELLED) {
            throw new InvalidBookingException("Booking with PNR " + pnr + " is already cancelled");
        }
        
        // Update booking status
        booking.setBookingStatus(Booking.BookingStatus.CANCELLED);
        
        // Restore flight seats
        Flight flight = booking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + booking.getNumberOfSeats());
        flightRepository.save(flight);
        
        bookingRepository.save(booking);
    }
    
    @Transactional
    public BookingResponse updateBooking(String pnr, BookingUpdateRequest request) {
        Booking booking = bookingRepository.findByPnr(pnr)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with PNR: " + pnr));
        
        // Clear existing passengers
        booking.getPassengers().clear();
        
        // Add new passengers
        for (PassengerDTO passengerDTO : request.getPassengers()) {
            Passenger passenger = new Passenger();
            passenger.setName(passengerDTO.getName());
            passenger.setGender(Passenger.Gender.valueOf(passengerDTO.getGender()));
            passenger.setAge(passengerDTO.getAge());
            passenger.setSeatNumber(passengerDTO.getSeatNumber());
            passenger.setMealPreference(Passenger.MealPreference.valueOf(passengerDTO.getMealPreference()));
            booking.addPassenger(passenger);
        }
        
        booking = bookingRepository.save(booking);
        return mapToBookingResponse(booking);
    }
    
    private BookingResponse mapToBookingResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setPnr(booking.getPnr());
        response.setFlightNumber(booking.getFlight().getFlightNumber());
        response.setFromPlace(booking.getFlight().getFromPlace());
        response.setToPlace(booking.getFlight().getToPlace());
        response.setDepartureDateTime(booking.getFlight().getDepartureDateTime());
        response.setArrivalDateTime(booking.getFlight().getArrivalDateTime());
        response.setUserName(booking.getUserName());
        response.setUserEmail(booking.getUserEmail());
        response.setNumberOfSeats(booking.getNumberOfSeats());
        response.setTotalPrice(booking.getTotalPrice());
        response.setBookingStatus(booking.getBookingStatus().name());
        response.setBookingDate(booking.getBookingDate());
        
        List<BookingResponse.PassengerInfo> passengers = booking.getPassengers().stream()
            .map(p -> new BookingResponse.PassengerInfo(
                p.getName(), p.getGender().name(), p.getAge(), 
                p.getSeatNumber(), p.getMealPreference().name()))
            .collect(Collectors.toList());
        response.setPassengers(passengers);
        
        return response;
    }
}
