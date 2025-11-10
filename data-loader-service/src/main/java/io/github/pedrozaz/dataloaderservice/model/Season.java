package io.github.pedrozaz.dataloaderservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seasons")
@Getter @Setter @NoArgsConstructor
public class Season {
    @Id
    @Column(name = "year")
    private Integer year;

    @Column(unique = true)
    private String url;
}
