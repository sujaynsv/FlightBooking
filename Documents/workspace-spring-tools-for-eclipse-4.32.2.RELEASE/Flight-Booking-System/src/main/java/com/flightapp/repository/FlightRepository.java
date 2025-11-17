package com.flightapp.repository;

import com.flightapp.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    
    Optional<Flight> findByFlightNumber(String flightNumber);
    
    @Query("SELECT f FROM Flight f WHERE f.fromPlace = :from AND f.toPlace = :to " +
           "AND f.departureDateTime >= :startDate AND f.departureDateTime < :endDate " +
           "AND f.status = 'SCHEDULED'")
    List<Flight> searchFlights(@Param("from") String from, 
                              @Param("to") String to, 
                              @Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate);
}
