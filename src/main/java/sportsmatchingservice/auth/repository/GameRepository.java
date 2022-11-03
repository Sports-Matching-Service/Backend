package sportsmatchingservice.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sportsmatchingservice.auth.domain.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}
