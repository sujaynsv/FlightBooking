package com.flightapp.repository;

import com.flightapp.model.*;
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
class BookingRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private FlightRepository flightRepository;
    
    @Autowired
    private AirlineRepository airlineRepository;
    
    private Airline airline;
    private Flight flight;
    private Booking booking;
    
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
        
        // Create and persist booking
        booking = new Booking();
        booking.setPnr("AB25321XYZ");
        booking.setFlight(flight);
        booking.setUserEmail("test@example.com");
        booking.setUserName("Test User");
        booking.setNumberOfSeats(2);
        booking.setTotalPrice(new BigDecimal("10000.00"));
        booking.setBookingStatus(Booking.BookingStatus.CONFIRMED);
        booking.setBookingDate(LocalDateTime.now());
        booking = entityManager.persist(booking);
        
        entityManager.flush();
    }
    
    @Test
    void testFindByPnr_Success() {
        // Act
        Optional<Booking> found = bookingRepository.findByPnr("AB25321XYZ");
        
        // Assert
        assertTrue(found.isPresent());
        assertEquals("AB25321XYZ", found.get().getPnr());
        assertEquals("test@example.com", found.get().getUserEmail());
        assertEquals(2, found.get().getNumberOfSeats());
    }
    
    @Test
    void testFindByPnr_NotFound() {
        // Act
        Optional<Booking> found = bookingRepository.findByPnr("INVALID");
        
        // Assert
        assertFalse(found.isPresent());
    }
    
    @Test
    void testFindByUserEmail_Success() {
        // Act
        List<Booking> bookings = bookingRepository.findByUserEmail("test@example.com");
        
        // Assert
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
        assertEquals("AB25321XYZ", bookings.get(0).getPnr());
    }
    
    @Test
    void testFindByUserEmail_NoResults() {
        // Act
        List<Booking> bookings = bookingRepository.findByUserEmail("invalid@example.com");
        
        // Assert
        assertNotNull(bookings);
        assertTrue(bookings.isEmpty());
    }
    
    @Test
    void testSaveBooking_Success() {
        // Arrange
        Booking newBooking = new Booking();
        newBooking.setPnr("CD45678ABC");
        newBooking.setFlight(flight);
        newBooking.setUserEmail("user2@example.com");
        newBooking.setUserName("User Two");
        newBooking.setNumberOfSeats(1);
        newBooking.setTotalPrice(new BigDecimal("5000.00"));
        newBooking.setBookingStatus(Booking.BookingStatus.CONFIRMED);
        newBooking.setBookingDate(LocalDateTime.now());
        
        // Act
        Booking saved = bookingRepository.save(newBooking);
        
        // Assert
        assertNotNull(saved.getId());
        assertEquals("CD45678ABC", saved.getPnr());
    }
    
    @Test
    void testUpdateBookingStatus_Success() {
        // Act
        booking.setBookingStatus(Booking.BookingStatus.CANCELLED);
        Booking updated = bookingRepository.save(booking);
        
        // Assert
        assertEquals(Booking.BookingStatus.CANCELLED, updated.getBookingStatus());
    }
    
    @Test
    void testDeleteBooking_Success() {
        // Act
        bookingRepository.deleteById(booking.getId());
        Optional<Booking> deleted = bookingRepository.findById(booking.getId());
        
        // Assert
        assertFalse(deleted.isPresent());
    }
    
    @Test
    void testBookingWithPassengers_Success() {
        // Arrange
        Passenger passenger1 = new Passenger();
        passenger1.setBooking(booking);
        passenger1.setName("John Doe");
        passenger1.setGender(Passenger.Gender.MALE);
        passenger1.setAge(30);
        passenger1.setSeatNumber("A1");
        passenger1.setMealPreference(Passenger.MealPreference.VEG);
        
        Passenger passenger2 = new Passenger();
        passenger2.setBooking(booking);
        passenger2.setName("Jane Doe");
        passenger2.setGender(Passenger.Gender.FEMALE);
        passenger2.setAge(28);
        passenger2.setSeatNumber("A2");
        passenger2.setMealPreference(Passenger.MealPreference.NON_VEG);
        
        booking.addPassenger(passenger1);
        booking.addPassenger(passenger2);
        
        // Act
        Booking savedBooking = bookingRepository.save(booking);
        entityManager.flush();
        entityManager.clear();
        
        Optional<Booking> found = bookingRepository.findById(savedBooking.getId());
        
        // Assert
        assertTrue(found.isPresent());
        assertEquals(2, found.get().getPassengers().size());
    }
}
