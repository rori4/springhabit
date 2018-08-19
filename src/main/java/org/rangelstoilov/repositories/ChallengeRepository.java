package org.rangelstoilov.repositories;

import org.rangelstoilov.entities.Challenge;
import org.rangelstoilov.custom.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, String> {
    List<Challenge> findAllByStatus(Status status);
}
