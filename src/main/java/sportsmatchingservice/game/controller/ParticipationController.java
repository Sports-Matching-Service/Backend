package sportsmatchingservice.game.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.constant.dto.ApiDataResponse;
import sportsmatchingservice.game.service.ParticipationService;

@RestController
@RequestMapping("/games/{game-id}/participate")
public class ParticipationController {
    private final ParticipationService participationService;

    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiDataResponse postParticipation(@PathVariable(value = "game-id") long gameId,
                                             Authentication authentication) {
        boolean result = participationService.createParticipation(gameId, authentication);

        if (result) {
            return ApiDataResponse.of(ErrorCode.CREATED, true);
        }
        return ApiDataResponse.of(ErrorCode.INTERNAL_ERROR, null);
    }
}