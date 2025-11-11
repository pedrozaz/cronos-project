package io.github.pedrozaz.dataloaderservice.repository;

import io.github.pedrozaz.dataloaderservice.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
}
