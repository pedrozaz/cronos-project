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
public class PitStopId implements Serializable {
    @Column(name = "race_id", nullable = false)
    private Long raceId;

    @Column(name = "driver_id", nullable = false)
    private Long driverId;

    @Column(name = "stop", nullable = false)
    private Integer stop;
}
