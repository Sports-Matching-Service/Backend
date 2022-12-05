package sportsmatchingservice.game.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sportsmatchingservice.constant.Gender;
import sportsmatchingservice.constant.Sport;
import sportsmatchingservice.constant.dto.ApiErrorResponse;
import sportsmatchingservice.game.dto.GamePostDto;
import sportsmatchingservice.game.dto.GameResponseDto;
import sportsmatchingservice.game.service.GameService;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.constant.dto.ApiDataResponse;
import sportsmatchingservice.game.service.ParticipationService;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;
    private final ParticipationService participationService;

    public GameController(GameService gameService, ParticipationService participationService) {
        this.gameService = gameService;
        this.participationService = participationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiDataResponse postGame(@RequestBody GamePostDto gamePostDto, Authentication authentication) {

        boolean result = gameService.createGame(gamePostDto, authentication);

        if (result) return ApiDataResponse.of(ErrorCode.CREATED, true);

        return ApiDataResponse.of(ErrorCode.INTERNAL_ERROR, null);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ApiDataResponse getValidGameListBySearchParams(
            @RequestParam(value="sport", required=false) Sport sport,
            @RequestParam(value="gender", required=false) Gender gender,
            @RequestParam(value="game_date", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate gameDate
    ){
        try {
            List<GameResponseDto> gameResponseDtos
                    = gameService.getValidGamesFilteredBy(sport, gender, gameDate);

            return ApiDataResponse.of(ErrorCode.OK, gameResponseDtos);
        } catch (Exception e) {
            return ApiDataResponse.of(ErrorCode.INTERNAL_ERROR, null);
        }
    }



}

