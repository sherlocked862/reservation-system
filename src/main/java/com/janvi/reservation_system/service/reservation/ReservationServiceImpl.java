package com.janvi.reservation_system.service.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.janvi.reservation_system.contract.ReservationContract;
import com.janvi.reservation_system.entity.ReservationDetails;
import com.janvi.reservation_system.entity.TrainDetails;
import com.janvi.reservation_system.enumss.ReservationStatus;
import com.janvi.reservation_system.repository.ReservationRepository;
import com.janvi.reservation_system.repository.TrainDetailsRepository;

@Service
public class ReservationServiceImpl implements ReservationService{


    private TrainDetailsRepository trainDetailsRepository;
    private ReservationRepository reservationRepository;

    public ReservationServiceImpl(TrainDetailsRepository trainDetailsRepository, ReservationRepository reservationRepository){
        this.trainDetailsRepository = trainDetailsRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public ReservationDetails createReservation(ReservationContract request) {
        TrainDetails train = trainDetailsRepository.findByTrainNumber(request.getTrainNumber())
                .orElseThrow(() -> new RuntimeException("Train not found"));

        int totalConfirmedSeats = reservationRepository.sumPassengersByTrainAndStatus(
                request.getTrainNumber(), ReservationStatus.CONFIRMED);

        ReservationDetails reservation = new ReservationDetails();
        reservation.setUserId(request.getUserName());
        reservation.setTrainNumber(request.getTrainNumber());
        reservation.setSource(train.getSource());
        reservation.setDestination(train.getDestination());
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setUpdatedAt(LocalDateTime.now());
        reservation.setNumberOfPassengers(request.getNumberOfPassengers());

        if (totalConfirmedSeats + request.getNumberOfPassengers() <= train.getTotalSeats()) {
            reservation.setReservationStatus(ReservationStatus.CONFIRMED);
        } else {
            reservation.setReservationStatus(ReservationStatus.WAITING);
            Integer maxWaiting = reservationRepository.findMaxWaitingNumber(request.getTrainNumber());
            reservation.setWaitingNumber((maxWaiting == null) ? 1 : maxWaiting + 1);
        }

        return reservationRepository.save(reservation);
    }


    @Override
    public List<ReservationDetails> getReservationsByUser(String userId) {
        return reservationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public boolean cancelReservation(UUID reservationId) {
        // Find the reservation to cancel
        ReservationDetails reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // Check if the reservation is already cancelled
        if (reservation.getReservationStatus() == ReservationStatus.CANCELLED) {
            throw new RuntimeException("Reservation is already cancelled");
        }

        // Set the reservation status to CANCELLED
        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        reservation.setUpdatedAt(LocalDateTime.now());

        // Save the updated reservation
        reservationRepository.save(reservation);

        // Promote waiting list reservations if any
        promoteWaitingList(reservation.getTrainNumber());

        return true;
    }

    @Override
    public Integer getAvailableSeats(String trainNumber){
        TrainDetails train = trainDetailsRepository.findByTrainNumber(trainNumber)
        .orElseThrow(() -> new RuntimeException("Train not found"));
        Integer confirmedSeats = reservationRepository.sumPassengersByTrainAndStatus(
            trainNumber, ReservationStatus.CONFIRMED
        );
    
        return train.getTotalSeats() - confirmedSeats;

    }

    private void promoteWaitingList(String trainNumber) {
        // Fetch the current confirmed reservations count
        TrainDetails train = trainDetailsRepository.findByTrainNumber(trainNumber)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        int totalConfirmedSeats = reservationRepository.sumPassengersByTrainAndStatus(trainNumber, ReservationStatus.CONFIRMED);

        // Fetch waiting reservations sorted by waiting number
        List<ReservationDetails> waitingReservations = reservationRepository
                .findByTrainNumberAndReservationStatusOrderByWaitingNumberAsc(trainNumber, ReservationStatus.WAITING);

        // Loop through waiting list and confirm reservations if seats are available
        for (ReservationDetails waitingReservation : waitingReservations) {
            if (totalConfirmedSeats + waitingReservation.getNumberOfPassengers() <= train.getTotalSeats()) {
                // Confirm the reservation
                waitingReservation.setReservationStatus(ReservationStatus.CONFIRMED);
                waitingReservation.setWaitingNumber(null);  // Clear waiting number
                waitingReservation.setUpdatedAt(LocalDateTime.now());
                reservationRepository.save(waitingReservation);

                // Increment confirmed seats
                totalConfirmedSeats += waitingReservation.getNumberOfPassengers();
            } else {
                // Break if we run out of seats
                break;
            }
        }
    }


    
}
