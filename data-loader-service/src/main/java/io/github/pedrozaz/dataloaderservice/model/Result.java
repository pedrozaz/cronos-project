package io.github.pedrozaz.dataloaderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "results", uniqueConstraints = {
        @UniqueConstraint(name = "uk_race_driver", columnNames = {"race_id", "driver_id"})
})
@Getter @Setter @NoArgsConstructor
public class Result {
    @Id
    @Column(name = "result_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constructor_id", nullable = false)
    private Constructor constructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    private Integer number;

    @Column(nullable = false)
    private Integer grid;

    @Column(name = "\"position\"")
    private Integer position;

    @Column(name = "position_text")
    private String positionText;

    @Column(nullable = false)
    private Float points;

    @Column(nullable = false)
    private Integer laps;

    @Column(name = "\"time\"")
    private String time;

    private Integer milliseconds;

    @Column(name = "fastest_lap")
    private Integer fastestLap;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "fastest_lap_time")
    private String fastestLapTime;

    @Column(name = "fastest_lap_speed")
    private Float fastestLapSpeed;
}
