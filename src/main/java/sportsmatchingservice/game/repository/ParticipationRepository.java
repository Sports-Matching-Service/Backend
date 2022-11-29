package sportsmatchingservice.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sportsmatchingservice.game.domain.Participation;

import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    Optional<Participation> findById(Long participationId);
}
