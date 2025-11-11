package io.github.pedrozaz.dataloaderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "races", uniqueConstraints = {
        @UniqueConstraint(name = "uk_year_round", columnNames = {"year", "round"})
})
@Getter @Setter @NoArgsConstructor
public class Race extends AbstractAuditableEntity{
    @Id
    @Column(name = "race_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long raceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year", nullable = false)
    private Season season;

    @Column(nullable = false)
    private Integer round;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "circuit_id", nullable = false)
    private Circuit circuit;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "\"time\"", columnDefinition = "TIME")
    private LocalTime time;

    @Column(unique = true)
    private String url;
}
