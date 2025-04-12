package com.janvi.reservation_system.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janvi.reservation_system.entity.TrainDetails;
import java.util.List;


public interface TrainDetailsRepository extends JpaRepository<TrainDetails,UUID> {
    boolean existsByTrainNumber(String trainNumber);
    Optional<TrainDetails> findByTrainNumber(String trainNumber);
    void deleteByTrainNumber(String trainNumber);
    List<TrainDetails> findBySourceAndDestination(String source, String destination);

}
