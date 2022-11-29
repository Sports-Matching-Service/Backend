package sportsmatchingservice.game.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public ParticipationService(
            ParticipationRepository participationRepository,
            GameRepository gameRepository
    ) {
        this.participationRepository = participationRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional
    public boolean deleteParticipation(Long gameId, Long participationId) {
        Optional<Participation> optionalParticipation
                = participationRepository.findById(participationId);
        Optional<Game> optionalGame = gameRepository.findById(gameId);

        if (optionalParticipation.isPresent() && optionalGame.isPresent()) {
            Participation participation = optionalParticipation.get();
            Game game = optionalGame.get();

            if (!participation.getGame().equals(game)) {
                throw new RuntimeException("일치하는 참여 기록이 존재하지 않습니다.");
            }
            if (game.getStartDateTime().minusHours(1).isBefore(LocalDateTime.now())) {
                throw new RuntimeException("해당 경기 참여를 취소할 수 없습니다.");
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
