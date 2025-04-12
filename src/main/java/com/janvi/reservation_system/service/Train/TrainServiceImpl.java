package com.janvi.reservation_system.service.Train;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.janvi.reservation_system.entity.TrainDetails;
import com.janvi.reservation_system.repository.TrainDetailsRepository;

import jakarta.transaction.Transactional;

@Service
public class TrainServiceImpl implements TrainService {

    private final TrainDetailsRepository trainDetailsRepository;
    public TrainServiceImpl(TrainDetailsRepository trainDetailsRepository){
        this.trainDetailsRepository = trainDetailsRepository;
    }

    @Override
    public TrainDetails createTrain(TrainDetails trainDetails) {
        boolean exists = trainDetailsRepository.existsByTrainNumber(trainDetails.getTrainNumber());
        if (exists) {
            throw new RuntimeException("Train number already exists");
        }

        trainDetails.setCreatedAt(LocalDateTime.now());
        trainDetails.setUpdatedAt(LocalDateTime.now());

        return trainDetailsRepository.save(trainDetails);
    }

    @Override
    public List<TrainDetails> getAllTrains() {
        return trainDetailsRepository.findAll();
    }

    @Override
    public TrainDetails getTrainByNumber(String number) {
        return trainDetailsRepository.findByTrainNumber(number)
                .orElseThrow(() -> new RuntimeException("Train with ID " + number + " not found"));
    }

    @Override
    public TrainDetails updateTrain(String id, TrainDetails newDetails) {
        TrainDetails existing = trainDetailsRepository.findByTrainNumber(id)
                .orElseThrow(() -> new RuntimeException("Train with ID " + id + " not found"));

        // If train number is being changed, validate uniqueness
        if (!existing.getTrainNumber().equals(newDetails.getTrainNumber())) {
            boolean exists = trainDetailsRepository.existsByTrainNumber(newDetails.getTrainNumber());
            if (exists) {
                throw new RuntimeException("Train number already exists");
            }
            existing.setTrainNumber(newDetails.getTrainNumber());
        }

        existing.setSource(newDetails.getSource());
        existing.setDestination(newDetails.getDestination());
        existing.setDepartureTime(newDetails.getDepartureTime());
        existing.setArrivalTime(newDetails.getArrivalTime());
        existing.setTotalSeats(newDetails.getTotalSeats());
        existing.setUpdatedAt(LocalDateTime.now());

        return trainDetailsRepository.save(existing);
    }

    @Transactional
    @Override
    public void deleteTrain(String id) {
        if (!trainDetailsRepository.existsByTrainNumber(id)) {
            throw new RuntimeException("Train with ID " + id + " does not exist");
        }
        trainDetailsRepository.deleteByTrainNumber(id);
    }

    @Override
    public void bulkCreateTrains(List<TrainDetails> trains){
        for (TrainDetails train : trains) {
            Optional<TrainDetails> existingTrain = trainDetailsRepository.findByTrainNumber(train.getTrainNumber());

            if (existingTrain.isPresent()) {
                // Update the existing train if it already exists
                TrainDetails trainToUpdate = existingTrain.get();
                trainToUpdate.setDepartureTime(train.getDepartureTime());
                trainToUpdate.setArrivalTime(train.getArrivalTime());
                trainToUpdate.setTotalSeats(train.getTotalSeats());
                trainToUpdate.setSource(train.getSource());
                trainToUpdate.setDestination(train.getDestination());
                trainToUpdate.setUpdatedAt(LocalDateTime.now());

                // Save the updated train
                trainDetailsRepository.save(trainToUpdate);
            } else {
                // If the train doesn't exist, create a new one
                train.setCreatedAt(LocalDateTime.now());
                train.setUpdatedAt(LocalDateTime.now());
                trainDetailsRepository.save(train);
            }
        }
    }
    
    @Override
    public List<TrainDetails> findBySourceAndDestination(String source, String destination) {
        return trainDetailsRepository.findBySourceAndDestination(source, destination);
    }

}

