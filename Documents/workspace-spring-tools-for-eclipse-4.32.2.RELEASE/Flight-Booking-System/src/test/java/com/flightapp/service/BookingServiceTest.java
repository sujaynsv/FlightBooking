package com.flightapp.service;

import com.flightapp.dto.*;
import com.flightapp.exception.InsufficientSeatsException;
import com.flightapp.exception.InvalidBookingException;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    
    @Mock
    private BookingRepository bookingRepository;
    
    @Mock
    private FlightRepository flightRepository;
    
    @Mock
    private PassengerRepository passengerRepository;
    
    @InjectMocks
    private BookingService bookingService;
    
    private Flight flight;
    private Booking booking;
    private BookingRequest bookingRequest;
    
    @BeforeEach
    void setUp() {
        // Setup airline
        Airline airline = new Airline();
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
        flight.setAvailableSeats(50);
        flight.setPrice(new BigDecimal("5000.00"));
        flight.setTripType(Flight.TripType.ONE_WAY);
        flight.setStatus(Flight.FlightStatus.SCHEDULED);
        
        // Setup passenger DTO
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setName("John Doe");
        passengerDTO.setGender("MALE");
        passengerDTO.setAge(30);
        passengerDTO.setSeatNumber("A1");
        passengerDTO.setMealPreference("VEG");
        
        List<PassengerDTO> passengers = new ArrayList<>();
        passengers.add(passengerDTO);
        
        // Setup booking request
        bookingRequest = new BookingRequest();
        bookingRequest.setFlightId(1L);
        bookingRequest.setUserEmail("test@example.com");
        bookingRequest.setUserName("Test User");
        bookingRequest.setNumberOfSeats(1);
        bookingRequest.setPassengers(passengers);
        
        // Setup booking
        booking = new Booking();
        booking.setId(1L);
        booking.setPnr("AB25321XYZ");
        booking.setFlight(flight);
        booking.setUserEmail("test@example.com");
        booking.setUserName("Test User");
        booking.setNumberOfSeats(1);
        booking.setTotalPrice(new BigDecimal("5000.00"));
        booking.setBookingStatus(Booking.BookingStatus.CONFIRMED);
        booking.setBookingDate(LocalDateTime.now());
    }
    
    @Test
    void testCreateBooking_Success() {
        // Arrange
        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        
        // Act
        BookingResponse response = bookingService.createBooking(bookingRequest);
        
        // Assert
        assertNotNull(response);
        assertEquals("AB25321XYZ", response.getPnr());
        assertEquals("test@example.com", response.getUserEmail());
        verify(flightRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }
    
    @Test
    void testCreateBooking_FlightNotFound() {
        // Arrange
        when(flightRepository.findById(anyLong())).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.createBooking(bookingRequest);
        });
    }
    
    @Test
    void testCreateBooking_InsufficientSeats() {
        // Arrange
        flight.setAvailableSeats(0);
        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        
        // Act & Assert
        assertThrows(InsufficientSeatsException.class, () -> {
            bookingService.createBooking(bookingRequest);
        });
    }
    
    @Test
    void testGetBookingByPNR_Success() {
        // Arrange
        when(bookingRepository.findByPnr("AB25321XYZ")).thenReturn(Optional.of(booking));
        
        // Act
        BookingResponse response = bookingService.getBookingByPNR("AB25321XYZ");
        
        // Assert
        assertNotNull(response);
        assertEquals("AB25321XYZ", response.getPnr());
    }
    
    @Test
    void testGetBookingByPNR_NotFound() {
        // Arrange
        when(bookingRepository.findByPnr("INVALID")).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.getBookingByPNR("INVALID");
        });
    }
    
    @Test
    void testCancelBooking_Success() {
        // Arrange
        when(bookingRepository.findByPnr("AB25321XYZ")).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        
        // Act
        bookingService.cancelBooking("AB25321XYZ");
        
        // Assert
        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(flightRepository, times(1)).save(any(Flight.class));
    }
    
    @Test
    void testCancelBooking_AlreadyCancelled() {
        // Arrange
        booking.setBookingStatus(Booking.BookingStatus.CANCELLED);
        when(bookingRepository.findByPnr("AB25321XYZ")).thenReturn(Optional.of(booking));
        
        // Act & Assert
        assertThrows(InvalidBookingException.class, () -> {
            bookingService.cancelBooking("AB25321XYZ");
        });
    }
}
