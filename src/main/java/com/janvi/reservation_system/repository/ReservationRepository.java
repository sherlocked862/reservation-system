package com.janvi.reservation_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.janvi.reservation_system.entity.ReservationDetails;
import com.janvi.reservation_system.enumss.ReservationStatus;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationDetails, UUID> {

    Integer countByTrainNumberAndReservationStatus(String trainNumber, ReservationStatus status);

    @Query("SELECT COALESCE(SUM(r.numberOfPassengers), 0) FROM ReservationDetails r WHERE r.trainNumber = :trainNumber AND r.reservationStatus = :status")
    Integer sumPassengersByTrainAndStatus(@Param("trainNumber") String trainNumber, @Param("status") ReservationStatus status);

    @Query("SELECT MAX(r.waitingNumber) FROM ReservationDetails r WHERE r.trainNumber = :trainNumber")
    Integer findMaxWaitingNumber(@Param("trainNumber") String trainNumber);

    List<ReservationDetails> findByUserIdOrderByCreatedAtDesc(String userId);

    List<ReservationDetails> findByTrainNumberAndReservationStatusOrderByWaitingNumberAsc(String trainNumber, ReservationStatus status);

    @Modifying
    @Query("UPDATE ReservationDetails r SET r.reservationStatus = :status, r.waitingNumber = NULL WHERE r.id = :reservationId")
    void updateReservationStatus(@Param("reservationId") UUID reservationId, @Param("status") ReservationStatus status);


}
