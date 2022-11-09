package sportsmatchingservice.game.repository.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import sportsmatchingservice.constant.Gender;
import sportsmatchingservice.constant.Sport;
import sportsmatchingservice.game.domain.Game;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static sportsmatchingservice.game.domain.QGame.game;


@Repository
@AllArgsConstructor
public class GameRepositoryCustomImpl implements GameRepositoryCustom {

    private final JPAQueryFactory query;


    @Override
    public List<Game> findValidGamesBySearchParams(
            Sport sport,
            Gender gender,
            LocalDate gameDate
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        if (sport != null) {
            builder.and(game.sport.eq(sport));
        }
        if (gender != null) {
            builder.and(game.gender.eq(gender));
        }
        if (gameDate != null) {
            builder.and(game.startDateTime.between(
                    gameDate.atStartOfDay(),
                    gameDate.plusDays(1).atStartOfDay()));
        } else {
            builder.and(game.startDateTime.after(
                    LocalDateTime.now(ZoneId.of("Asia/Seoul"))));
        }

        return query.selectFrom(game)
                .where(builder)
                .fetch();
    }

}
