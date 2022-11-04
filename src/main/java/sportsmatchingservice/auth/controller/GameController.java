package sportsmatchingservice.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sportsmatchingservice.auth.dto.GamePostDto;
import sportsmatchingservice.auth.service.GameService;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.constant.dto.ApiDataResponse;



@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiDataResponse postGame(@RequestBody GamePostDto gamePostDto, Authentication authentication) {

        boolean result = gameService.createGame(gamePostDto, authentication);

        if (result) return ApiDataResponse.of(ErrorCode.CREATED, true);

        return ApiDataResponse.of(ErrorCode.INTERNAL_ERROR, null);
    }
}
