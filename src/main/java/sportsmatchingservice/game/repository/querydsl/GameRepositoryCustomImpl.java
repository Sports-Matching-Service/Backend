package sportsmatchingservice.game.repository.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import sportsmatchingservice.constant.Gender;
import sportsmatchingservice.constant.Sport;
import sportsmatchingservice.game.domain.Game;

import java.time.LocalDate;
import java.util.List;

import static sportsmatchingservice.game.domain.QGame.game;


@Repository
@AllArgsConstructor
public class GameRepositoryCustomImpl implements GameRepositoryCustom {

    private final JPAQueryFactory query;


    @Override
    public List<Game> findBySearchParams(
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

        List<Game> games =  query.selectFrom(game)
                .where(builder)
                .fetch();

        return games;
    }

}
