package com.janvi.reservation_system.service.reservation;

import java.util.List;
import java.util.UUID;

import com.janvi.reservation_system.contract.ReservationContract;
import com.janvi.reservation_system.entity.ReservationDetails;

public interface ReservationService {
    ReservationDetails createReservation(ReservationContract request);
    List<ReservationDetails> getReservationsByUser(String userId);
    boolean cancelReservation(UUID reservationId);
    Integer getAvailableSeats(String trainNumber);

}
