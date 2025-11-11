package io.github.pedrozaz.dataloaderservice.repository;

import io.github.pedrozaz.dataloaderservice.model.PitStop;
import io.github.pedrozaz.dataloaderservice.model.PitStopId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PitStopRepository extends JpaRepository<PitStop, PitStopId> {
}
