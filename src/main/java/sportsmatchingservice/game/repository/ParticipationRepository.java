package sportsmatchingservice.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sportsmatchingservice.participation.domain.Participation;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

}
