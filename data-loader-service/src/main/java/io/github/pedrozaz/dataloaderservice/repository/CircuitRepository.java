package io.github.pedrozaz.dataloaderservice.repository;

import io.github.pedrozaz.dataloaderservice.model.Circuit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CircuitRepository extends JpaRepository<Circuit, Long> {
    Optional<Circuit> findByCircuitRef(String circuitRef);
}
