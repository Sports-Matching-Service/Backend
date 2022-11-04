package sportsmatchingservice.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sportsmatchingservice.auth.domain.Game;
import sportsmatchingservice.auth.domain.User;
import sportsmatchingservice.auth.dto.GamePostDto;
import sportsmatchingservice.auth.repository.GameRepository;
import sportsmatchingservice.auth.repository.UserRepository;

import java.util.Optional;

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
}
