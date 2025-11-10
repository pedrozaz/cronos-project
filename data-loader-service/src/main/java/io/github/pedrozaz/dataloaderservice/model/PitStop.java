package io.github.pedrozaz.dataloaderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pit_stops", uniqueConstraints = {
        @UniqueConstraint(name = "uk_race_driver_stop", columnNames = {"race_id", "driver_id", "stop"})
})
@Getter @Setter @NoArgsConstructor
public class PitStop {
    @Id
    @Column(name = "pit_stop_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pitStopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(nullable = false)
    private Integer stop;

    @Column(nullable = false)
    private Integer lap;

    @Column(name = "\"time\"", nullable = false)
    private LocalDateTime time;

    private String duration;
    private Integer milliseconds;
}
