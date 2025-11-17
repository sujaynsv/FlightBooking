package com.flightapp.repository;

import com.flightapp.model.Airline;
import com.flightapp.model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class FlightRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private AirlineRepository airlineRepository;
    
    private Airline airline;
    private Flight flight;
    
    @BeforeEach
    void setUp() {
        // Create and persist airline
        airline = new Airline();
        airline.setAirlineName("Test Airlines");
        airline.setAirlineCode("TA");
        airline.setContactNumber("1234567890");
        airline = entityManager.persist(airline);
        
        // Create and persist flight
        flight = new Flight();
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
        flight = entityManager.persist(flight);
        
        entityManager.flush();
    }
    
    @Test
    void testFindByFlightNumber_Success() {
        // Act
        Optional<Flight> found = flightRepository.findByFlightNumber("TA1234");
        
        // Assert
        assertTrue(found.isPresent());
        assertEquals("TA1234", found.get().getFlightNumber());
        assertEquals("Delhi", found.get().getFromPlace());
        assertEquals("Mumbai", found.get().getToPlace());
    }
    
    @Test
    void testFindByFlightNumber_NotFound() {
        // Act
        Optional<Flight> found = flightRepository.findByFlightNumber("INVALID");
        
        // Assert
        assertFalse(found.isPresent());
    }
    
    @Test
    void testSearchFlights_Success() {
        // Arrange
        LocalDateTime departureTime = flight.getDepartureDateTime();
        LocalDateTime startDate = departureTime.toLocalDate().atStartOfDay();
        LocalDateTime endDate = startDate.plusDays(1);
        
        // Act
        List<Flight> flights = flightRepository.searchFlights("Delhi", "Mumbai", startDate, endDate);
        
        // Assert
        assertNotNull(flights);
        assertEquals(1, flights.size());
        assertEquals("TA1234", flights.get(0).getFlightNumber());
    }
    
    @Test
    void testSearchFlights_NoResults() {
        // Arrange
        LocalDateTime searchDate = LocalDateTime.now().plusDays(10);
        LocalDateTime startDate = searchDate.toLocalDate().atStartOfDay();
        LocalDateTime endDate = startDate.plusDays(1);
        
        // Act
        List<Flight> flights = flightRepository.searchFlights("Delhi", "Bangalore", startDate, endDate);
        
        // Assert
        assertNotNull(flights);
        assertTrue(flights.isEmpty());
    }
    
    @Test
    void testSaveFlight_Success() {
        // Arrange
        Flight newFlight = new Flight();
        newFlight.setFlightNumber("TA5678");
        newFlight.setAirline(airline);
        newFlight.setFromPlace("Bangalore");
        newFlight.setToPlace("Chennai");
        newFlight.setDepartureDateTime(LocalDateTime.now().plusDays(2));
        newFlight.setArrivalDateTime(LocalDateTime.now().plusDays(2).plusHours(1));
        newFlight.setTotalSeats(150);
        newFlight.setAvailableSeats(150);
        newFlight.setPrice(new BigDecimal("3000.00"));
        newFlight.setTripType(Flight.TripType.ONE_WAY);
        newFlight.setStatus(Flight.FlightStatus.SCHEDULED);
        
        // Act
        Flight saved = flightRepository.save(newFlight);
        
        // Assert
        assertNotNull(saved.getId());
        assertEquals("TA5678", saved.getFlightNumber());
    }
    
    @Test
    void testDeleteFlight_Success() {
        // Act
        flightRepository.deleteById(flight.getId());
        Optional<Flight> deleted = flightRepository.findById(flight.getId());
        
        // Assert
        assertFalse(deleted.isPresent());
    }
}
