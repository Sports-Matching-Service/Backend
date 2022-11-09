package sportsmatchingservice.game.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sportsmatchingservice.constant.Gender;
import sportsmatchingservice.constant.Sport;
import sportsmatchingservice.game.domain.Game;
import sportsmatchingservice.auth.domain.User;
import sportsmatchingservice.game.dto.GamePostDto;
import sportsmatchingservice.game.dto.GameResponseDto;
import sportsmatchingservice.game.repository.GameRepository;
import sportsmatchingservice.auth.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public boolean createGame(GamePostDto gamePostDto, Authentication authentication) {
        if(gamePostDto == null) return false;

        String email = (String) authentication.getPrincipal();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        Game game = gamePostDto.toEntity();
        game.setCurrentRecruitment(0);
        game.setDeadline(game.getEndDateTime().minusHours(1));

        if (optionalUser.isPresent()) game.setHost(optionalUser.get());

        gameRepository.save(game);

        return true;
    }

    public List<GameResponseDto> getValidGamesFilteredBy(Sport sport, Gender gender, LocalDate gameDate){

        List<GameResponseDto> gameResponseDtos = gameRepository.findValidGamesBySearchParams(sport, gender, gameDate)
                .stream()
                .map(game -> GameResponseDto.of(game))
                .collect(Collectors.toList());

        return gameResponseDtos;
    }
}
