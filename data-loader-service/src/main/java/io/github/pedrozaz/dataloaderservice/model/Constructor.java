package io.github.pedrozaz.dataloaderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "constructors")
@Getter @Setter @NoArgsConstructor
public class Constructor extends AbstractAuditableEntity {
    @Id
    @Column(name = "constructor_id")
    private Long constructorId;

    @Column(name = "constructor_ref", nullable = false, unique = true)
    private String constructorRef;

    @Column(nullable = false, unique = true)
    private String name;

    private String nationality;
    private String url;
}
