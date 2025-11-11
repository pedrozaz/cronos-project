package io.github.pedrozaz.dataloaderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "circuits")
@Getter @Setter @NoArgsConstructor
public class Circuit extends AbstractAuditableEntity {
    @Id
    @Column(name = "circuit_id")
    private Long id;

    @Column(name = "circuit_ref", nullable = false, unique = true)
    private String circuitRef;

    @Column(nullable = false)
    private String name;

    private String location;
    private String country;
    private Float latitude;
    private Float longitude;
    private Integer altitude;
}
