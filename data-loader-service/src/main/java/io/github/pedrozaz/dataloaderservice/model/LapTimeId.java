package io.github.pedrozaz.dataloaderservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LapTimeId implements Serializable {
    @Column(name = "race_id", nullable = false)
    private Integer raceId;

    @Column(name = "driver_id", nullable = false)
    private Integer driverId;

    @Column(name = "lap", nullable = false)
    private Integer lap;
}
