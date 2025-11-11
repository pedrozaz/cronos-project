package io.github.pedrozaz.dataloaderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "drivers")
@Getter @Setter @NoArgsConstructor
public class Driver extends AbstractAuditableEntity {
    @Id
    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "driver_ref", nullable = false, unique = true)
    private String driverRef;

    private Integer number;

    @Column(length = 3)
    private String code;

    @Column(nullable = false)
    private String forename;

    @Column(nullable = false)
    private String surname;

    private LocalDate dob;
    private String nationality;
    private String url;
}
