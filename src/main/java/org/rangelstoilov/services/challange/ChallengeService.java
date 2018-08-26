package org.rangelstoilov.services.challange;

import org.rangelstoilov.entities.Challenge;
import org.rangelstoilov.models.challenge.ChallengeViewModel;

import java.util.List;

public interface ChallengeService {
    ChallengeViewModel sendChallenge(String id, String userEmail);

    List<Challenge> getAllByOpponent(String name);
}
