package org.rangelstoilov.services.challange;

import org.modelmapper.ModelMapper;
import org.rangelstoilov.entities.Challenge;
import org.rangelstoilov.entities.User;
import org.rangelstoilov.models.challenge.ChallengeViewModel;
import org.rangelstoilov.repositories.ChallengeRepository;
import org.rangelstoilov.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ChallengeServiceImpl implements ChallengeService {
    private final UserService userService;
    private final ChallengeRepository challengeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ChallengeServiceImpl(UserService userService, ChallengeRepository challengeRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.challengeRepository = challengeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ChallengeViewModel sendChallenge(String id, String userEmail) {
        User challenger = this.userService.findFirstByEmail(userEmail);
        User opponent = this.userService.findFirstById(id);
        if(challenger.getChallengesCreated().contains(opponent) || challenger.getChallengesAccepted().contains(opponent)){
            return null;
        } else {
            Challenge challenge = new Challenge(challenger,opponent);
            challengeRepository.save(challenge);
            return this.modelMapper.map(challenge,ChallengeViewModel.class);
        }
    }

    @Override
    public List<Challenge> getAllByOpponent(String opponentEmail) {
        return this.challengeRepository.findAllByOpponentEmail(opponentEmail);
    }
}
