package io.github.pedrozaz.dataloaderservice.repository;

import io.github.pedrozaz.dataloaderservice.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
    Optional<Race> findBySeasonYearAndRound(Integer year, Integer round);
}
