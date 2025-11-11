package io.github.pedrozaz.dataloaderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lap_times")
@Getter @Setter @NoArgsConstructor
public class LapTime {

    @EmbeddedId
    private LapTimeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", updatable = false, insertable = false)
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", updatable = false, insertable = false)
    private Driver driver;

    @Column(name = "\"position\"")
    private Integer position;

    @Column(name = "\"time\"")
    private String time;

    @Column(nullable = false)
    private Integer milliseconds;
}
