package sportsmatchingservice.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sportsmatchingservice.game.domain.Participation;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

}
