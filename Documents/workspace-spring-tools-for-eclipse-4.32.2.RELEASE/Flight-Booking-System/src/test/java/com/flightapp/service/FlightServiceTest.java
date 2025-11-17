package com.flightapp.service;

import com.flightapp.dto.*;
import com.flightapp.exception.ResourceNotFoundException;
import com.flightapp.model.*;
import com.flightapp.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {
    
    @Mock
    private FlightRepository flightRepository;
    
    @Mock
    private AirlineRepository airlineRepository;
    
    @InjectMocks
    private FlightService flightService;
    
    private Airline airline;
    private Flight flight;
    private InventoryRequest inventoryRequest;
    
    @BeforeEach
    void setUp() {
        // Setup airline
        airline = new Airline();
        airline.setId(1L);
        airline.setAirlineName("Test Airlines");
        airline.setAirlineCode("TA");
        
        // Setup flight
        flight = new Flight();
        flight.setId(1L);
        flight.setFlightNumber("TA1234");
        flight.setAirline(airline);
        flight.setFromPlace("Delhi");
        flight.setToPlace("Mumbai");
        flight.setDepartureDateTime(LocalDateTime.now().plusDays(1));
        flight.setArrivalDateTime(LocalDateTime.now().plusDays(1).plusHours(2));
        flight.setTotalSeats(100);
        flight.setAvailableSeats(100);
        flight.setPrice(new BigDecimal("5000.00"));
        flight.setTripType(Flight.TripType.ONE_WAY);
        flight.setStatus(Flight.FlightStatus.SCHEDULED);
        
        // Setup inventory request
        inventoryRequest = new InventoryRequest();
        inventoryRequest.setFlightNumber("TA1234");
        inventoryRequest.setAirlineId(1L);
        inventoryRequest.setFromPlace("Delhi");
        inventoryRequest.setToPlace("Mumbai");
        inventoryRequest.setDepartureDateTime(LocalDateTime.now().plusDays(1));
        inventoryRequest.setArrivalDateTime(LocalDateTime.now().plusDays(1).plusHours(2));
        inventoryRequest.setTotalSeats(100);
        inventoryRequest.setPrice(new BigDecimal("5000.00"));
        inventoryRequest.setTripType("ONE_WAY");
    }
    
    @Test
    void testSearchFlights_Success() {
        // Arrange
        FlightSearchRequest request = new FlightSearchRequest();
        request.setFromPlace("Delhi");
        request.setToPlace("Mumbai");
        request.setDepartureDate(LocalDate.now().plusDays(1));
        
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        
        when(flightRepository.searchFlights(
            anyString(), 
            anyString(), 
            any(LocalDateTime.class), 
            any(LocalDateTime.class)
        )).thenReturn(flights);
        
        // Act
        List<FlightSearchResponse> responses = flightService.searchFlights(request);
        
        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("TA1234", responses.get(0).getFlightNumber());
        assertEquals("Delhi", responses.get(0).getFromPlace());
        assertEquals("Mumbai", responses.get(0).getToPlace());
        
        verify(flightRepository, times(1)).searchFlights(
            eq("Delhi"),
            eq("Mumbai"),
            any(LocalDateTime.class),
            any(LocalDateTime.class)
        );
    }
    
    @Test
    void testSearchFlights_NoAvailableSeats() {
        // Arrange
        FlightSearchRequest request = new FlightSearchRequest();
        request.setFromPlace("Delhi");
        request.setToPlace("Mumbai");
        request.setDepartureDate(LocalDate.now().plusDays(1));
        
        flight.setAvailableSeats(0);
        
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        
        when(flightRepository.searchFlights(
            anyString(), 
            anyString(), 
            any(LocalDateTime.class), 
            any(LocalDateTime.class)
        )).thenReturn(flights);
        
        // Act
        List<FlightSearchResponse> responses = flightService.searchFlights(request);
        
        // Assert
        assertNotNull(responses);
        assertEquals(0, responses.size());
    }
    
    @Test
    void testSearchFlights_EmptyResult() {
        // Arrange
        FlightSearchRequest request = new FlightSearchRequest();
        request.setFromPlace("Delhi");
        request.setToPlace("Bangalore");
        request.setDepartureDate(LocalDate.now().plusDays(1));
        
        when(flightRepository.searchFlights(
            anyString(), 
            anyString(), 
            any(LocalDateTime.class), 
            any(LocalDateTime.class)
        )).thenReturn(new ArrayList<>());
        
        // Act
        List<FlightSearchResponse> responses = flightService.searchFlights(request);
        
        // Assert
        assertNotNull(responses);
        assertEquals(0, responses.size());
    }
    
    @Test
    void testAddFlight_Success() {
        // Arrange
        when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        
        // Act
        Flight result = flightService.addFlight(inventoryRequest);
        
        // Assert
        assertNotNull(result);
        assertEquals("TA1234", result.getFlightNumber());
        assertEquals(100, result.getTotalSeats());
        verify(airlineRepository, times(1)).findById(1L);
        verify(flightRepository, times(1)).save(any(Flight.class));
    }
    
    @Test
    void testAddFlight_AirlineNotFound() {
        // Arrange
        when(airlineRepository.findById(1L)).thenReturn(Optional.empty());
        
        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class, 
            () -> flightService.addFlight(inventoryRequest)
        );
        
        assertTrue(exception.getMessage().contains("Airline not found"));
        verify(flightRepository, never()).save(any(Flight.class));
    }
}
