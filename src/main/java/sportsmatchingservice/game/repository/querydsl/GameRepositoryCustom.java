package sportsmatchingservice.game.repository.querydsl;

import sportsmatchingservice.constant.Gender;
import sportsmatchingservice.constant.Sport;
import sportsmatchingservice.game.domain.Game;
import sportsmatchingservice.game.dto.GameResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface GameRepositoryCustom {

    List<Game> findValidGamesBySearchParams(Sport sport, Gender gender, LocalDate gameDate);
}
