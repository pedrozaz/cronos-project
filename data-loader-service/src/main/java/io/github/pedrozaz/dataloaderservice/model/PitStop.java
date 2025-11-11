package io.github.pedrozaz.dataloaderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "pit_stops")
@Getter @Setter @NoArgsConstructor
public class PitStop {

    @EmbeddedId
    private PitStopId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", updatable = false, insertable = false)
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", updatable = false, insertable = false)
    private Driver driver;

    @Column(nullable = false)
    private Integer lap;

    @Column(name = "\"time\"", nullable = false, columnDefinition = "TIME")
    private LocalTime time;

    private String duration;
    private Integer milliseconds;
}
