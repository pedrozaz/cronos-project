package io.github.pedrozaz.dataloaderservice.repository;

import io.github.pedrozaz.dataloaderservice.model.LapTime;
import io.github.pedrozaz.dataloaderservice.model.LapTimeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LapTimeRepository extends JpaRepository<LapTime, LapTimeId> {
}
