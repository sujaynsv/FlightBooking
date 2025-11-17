package com.flightapp.service;

import com.flightapp.dto.*;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.Flight;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private AirlineRepository airlineRepository;
    
    public List<FlightSearchResponse> searchFlights(FlightSearchRequest request) {
        // Convert LocalDate to start and end of day
        LocalDateTime startDate = request.getDepartureDate().atStartOfDay();
        LocalDateTime endDate = request.getDepartureDate().plusDays(1).atStartOfDay();
        
        List<Flight> flights = flightRepository.searchFlights(
            request.getFromPlace(),
            request.getToPlace(),
            startDate,
            endDate
        );
        
        // Filter flights with available seats
        return flights.stream()
            .filter(f -> f.getAvailableSeats() > 0)
            .map(this::mapToSearchResponse)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public Flight addFlight(InventoryRequest request) {
        Flight flight = new Flight();
        flight.setFlightNumber(request.getFlightNumber());
        
        // Validate airline exists fixed my bug
        flight.setAirline(airlineRepository.findById(request.getAirlineId())
            .orElseThrow(() -> new ResourceNotFoundException("Airline not found with id: " + request.getAirlineId())));
        
        flight.setFromPlace(request.getFromPlace());
        flight.setToPlace(request.getToPlace());
        flight.setDepartureDateTime(request.getDepartureDateTime());
        flight.setArrivalDateTime(request.getArrivalDateTime());
        flight.setTotalSeats(request.getTotalSeats());
        flight.setAvailableSeats(request.getTotalSeats());
        flight.setPrice(request.getPrice());
        flight.setTripType(Flight.TripType.valueOf(request.getTripType()));
        flight.setStatus(Flight.FlightStatus.SCHEDULED);
        
        return flightRepository.save(flight);
    }
    
    private FlightSearchResponse mapToSearchResponse(Flight flight) {
        FlightSearchResponse response = new FlightSearchResponse();
        response.setId(flight.getId());
        response.setFlightNumber(flight.getFlightNumber());
        response.setAirlineName(flight.getAirline().getAirlineName());
        response.setAirlineCode(flight.getAirline().getAirlineCode());
        response.setFromPlace(flight.getFromPlace());
        response.setToPlace(flight.getToPlace());
        response.setDepartureDateTime(flight.getDepartureDateTime());
        response.setArrivalDateTime(flight.getArrivalDateTime());
        response.setAvailableSeats(flight.getAvailableSeats());
        response.setPrice(flight.getPrice());
        response.setTripType(flight.getTripType().name());
        response.setStatus(flight.getStatus().name());
        response.setDuration(calculateDuration(flight));
        return response;
    }
    
    private String calculateDuration(Flight flight) {
        Duration duration = Duration.between(flight.getDepartureDateTime(), flight.getArrivalDateTime());
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return hours + "h " + minutes + "m";
    }
}
