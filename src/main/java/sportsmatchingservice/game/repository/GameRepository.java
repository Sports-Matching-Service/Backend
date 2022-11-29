package sportsmatchingservice.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sportsmatchingservice.game.domain.Game;
import sportsmatchingservice.game.repository.querydsl.GameRepositoryCustom;

import java.util.Optional;


public interface GameRepository extends JpaRepository<Game, Long>, GameRepositoryCustom {

    Optional<Game> findById(Long id);
}


