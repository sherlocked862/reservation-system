package com.janvi.reservation_system.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationContract {
    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("train_number")
    private String trainNumber;

    @JsonProperty("number_of_passengers")
    private Integer numberOfPassengers;
}
