package io.github.pedrozaz.dataloaderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lap_times", uniqueConstraints = {
        @UniqueConstraint(name = "uk_race_driver_lap", columnNames = {"race_id", "driver_id", "lap"})
})
@Getter @Setter @NoArgsConstructor
public class LapTime {
    @Id
    @Column(name = "lap_time_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lapTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(nullable = false)
    private Integer lap;

    @Column(name = "\"position\"")
    private String position;

    @Column(name = "\"time\"")
    private String time;

    @Column(nullable = false)
    private Integer milliseconds;
}
