//package org.rangelstoilov.repositories;
//
//import org.rangelstoilov.custom.enums.TaskStatus;
//import org.rangelstoilov.entities.Challenge;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface ChallengeRepository extends JpaRepository<Challenge, String> {
//    List<Challenge> findAllByChallengeStatus(TaskStatus taskStatus);
//    List<Challenge> findAllByOpponentEmail(String opponentEmail);
//}
