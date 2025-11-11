package io.github.pedrozaz.dataloaderservice.repository;

import io.github.pedrozaz.dataloaderservice.model.PitStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PitStopRepository extends JpaRepository<PitStop, Long> {
}
