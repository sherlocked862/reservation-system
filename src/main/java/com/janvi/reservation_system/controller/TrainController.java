package com.janvi.reservation_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.janvi.reservation_system.entity.TrainDetails;
import com.janvi.reservation_system.service.Train.TrainService;

@RestController
@RequestMapping("api/v1")
public class TrainController {
    private final TrainService trainService;

    public TrainController(TrainService trainService){
        this.trainService = trainService;
    }

    @PostMapping("train/create")
    public ResponseEntity<TrainDetails> createTrain(@RequestBody TrainDetails trainDetails) {
        TrainDetails createdTrain = trainService.createTrain(trainDetails);
        return ResponseEntity.ok(createdTrain);
    }

    @PostMapping("/bulkCreateTrains")
    public ResponseEntity<String> bulkCreateTrains(@RequestBody List<TrainDetails> trains) {
        trainService.bulkCreateTrains(trains);
        return ResponseEntity.ok("Trains processed successfully");
    }

    @GetMapping("train/all")
    public ResponseEntity<List<TrainDetails>> getAllTrains() {
        List<TrainDetails> trains = trainService.getAllTrains();
        return ResponseEntity.ok(trains);
    }

    @GetMapping("train/{id}")
    public ResponseEntity<TrainDetails> getTrainByNumber(@PathVariable String id) {
        TrainDetails train = trainService.getTrainByNumber(id);
        return ResponseEntity.ok(train);
    }

    @PutMapping("train/update/{trainNumber}")
    public ResponseEntity<String> updateTrain(@PathVariable String trainNumber, @RequestBody TrainDetails trainDetails) {
        try {
            trainService.updateTrain(trainNumber, trainDetails);
            return ResponseEntity.ok("Train updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @DeleteMapping("train/delete/{trainNumber}")
    public ResponseEntity<String> deleteTrain(@PathVariable String trainNumber) {
        trainService.deleteTrain(trainNumber);
        return ResponseEntity.ok("Train deleted successfully");
    }

    @GetMapping("train/search")
    public ResponseEntity<List<TrainDetails>> searchTrains(
            @RequestParam String source,
            @RequestParam String destination) {

        List<TrainDetails> trains = trainService.findBySourceAndDestination(source, destination);
        return ResponseEntity.ok(trains);
    }

    
}
