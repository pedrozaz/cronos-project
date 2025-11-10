package io.github.pedrozaz.dataloaderservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@MappedSuperclass
@Getter
public abstract class AbstractAuditableEntity {

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false,
            columnDefinition = "TIMESTAMPTZ DEFAULT (NOW() AT TIME ZONE 'utc')")
    private Instant createdAt = Instant.now();
}
