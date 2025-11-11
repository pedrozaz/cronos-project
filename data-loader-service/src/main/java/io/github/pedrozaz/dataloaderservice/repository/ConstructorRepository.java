package io.github.pedrozaz.dataloaderservice.repository;

import io.github.pedrozaz.dataloaderservice.model.Constructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstructorRepository extends JpaRepository<Constructor, Long> {
    Optional<Constructor> findByConstructorRef(String constructorRef);
}
