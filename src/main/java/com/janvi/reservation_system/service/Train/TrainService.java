package com.janvi.reservation_system.service.Train;

import java.util.List;

import com.janvi.reservation_system.entity.TrainDetails;

public interface TrainService {
    TrainDetails createTrain(TrainDetails trainDetails);
    void bulkCreateTrains(List<TrainDetails> trains);
    List<TrainDetails> getAllTrains();
    TrainDetails getTrainByNumber(String id);
    void deleteTrain(String id);
    TrainDetails updateTrain(String id, TrainDetails trainDetails);
    List<TrainDetails> findBySourceAndDestination(String source, String destination);
}
