package sportsmatchingservice.game.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sportsmatchingservice.auth.domain.User;
import sportsmatchingservice.auth.repository.UserRepository;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.exceptions.exception.GeneralException;
import sportsmatchingservice.game.domain.Game;
import sportsmatchingservice.game.domain.Participation;
import sportsmatchingservice.game.repository.GameRepository;
import sportsmatchingservice.game.repository.ParticipationRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public ParticipationService(ParticipationRepository participationRepository, GameRepository gameRepository, UserRepository userRepository) {
        this.participationRepository = participationRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public boolean createParticipation(long gameId,
                                       Authentication authentication) {

        Participation participation = new Participation();
        String email = (String) authentication.getPrincipal();

        Game game = verifiedGame(gameId);
        game.setCurrentRecruitment(game.getCurrentRecruitment() + 1);

        participation.setGame(game);
        participation.setUser(verifiedUser(email));

        participationRepository.save(participation);

        return true;
    }

    public Game verifiedGame(long gameId) {
        Optional<Game> game = gameRepository.findById(gameId);

        return game.orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND, new Exception()));
    }

    public User verifiedUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND, new Exception()));
    }

    public boolean deleteParticipation(Long gameId, Long participationId) {
        Optional<Participation> optionalParticipation
                = participationRepository.findById(participationId);
        Optional<Game> optionalGame = gameRepository.findById(gameId);

        if (optionalParticipation.isPresent() && optionalGame.isPresent()) {
            Participation participation = optionalParticipation.get();
            Game game = optionalGame.get();

            if (!participation.getGame().equals(game)) {
                throw new GeneralException(ErrorCode.NOT_FOUND, null);
            }
            if (game.getStartDateTime().minusHours(1).isBefore(LocalDateTime.now())) {
                throw new GeneralException(ErrorCode.NOT_ALLOWED, null);
            }
            game.setCurrentRecruitment(game.getCurrentRecruitment()-1);
            participation.setDeletedAt(LocalDateTime.now());
            gameRepository.save(game);
            participationRepository.save(participation);
            return true;
        }
        return false;
    }
}
