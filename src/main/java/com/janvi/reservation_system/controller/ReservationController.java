package com.janvi.reservation_system.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janvi.reservation_system.contract.ReservationContract;
import com.janvi.reservation_system.entity.ReservationDetails;
import com.janvi.reservation_system.service.reservation.ReservationService;

@RestController
@RequestMapping("api/v1")
public class ReservationController {
    private ReservationService reservationService; 

    
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping("reservations")
    public ResponseEntity<ReservationDetails> createReservation(@RequestBody ReservationContract request) {
        ReservationDetails reservation = reservationService.createReservation(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    @GetMapping("reservations/user/{userId}")
    public ResponseEntity<List<ReservationDetails>> getReservationsByUser(@PathVariable String userId) {
        List<ReservationDetails> reservations = reservationService.getReservationsByUser(userId);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("reservations/cancel/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable UUID reservationId) {
        boolean cancelled = reservationService.cancelReservation(reservationId);
        if (cancelled) {
            return ResponseEntity.ok("Reservation cancelled successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to cancel reservation.");
        }
    }

    @GetMapping("reservations/available-seats/{trainNumber}")
    public ResponseEntity<Integer> getAvailableSeats(@PathVariable String trainNumber) {
        int availableSeats = reservationService.getAvailableSeats(trainNumber);
        return ResponseEntity.ok(availableSeats);
    }
}
