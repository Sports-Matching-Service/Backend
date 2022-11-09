package sportsmatchingservice.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sportsmatchingservice.game.domain.Game;

public interface GameRepository extends JpaRepository<Game, Long>, GameRepositoryCustom {
}
