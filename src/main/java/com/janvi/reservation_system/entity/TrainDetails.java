package com.janvi.reservation_system.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trains")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainDetails {
    @Id
    @UuidGenerator
    @GeneratedValue
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(nullable = false, unique = true, name = "train_number")
    private String trainNumber;

    @Column(nullable = false, name = "source")
    private String source;

    @Column(nullable = false, name = "destination")
    private String destination;

    @Column(nullable = false, name = "departure_time")
    private LocalDateTime departureTime;

    @Column(nullable = false, name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(nullable = false, name = "total_seats")
    private Integer totalSeats;

    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
