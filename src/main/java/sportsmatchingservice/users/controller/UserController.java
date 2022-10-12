package sportsmatchingservice.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.constant.dto.ApiDataResponse;
import sportsmatchingservice.users.dto.UserPostDto;
import sportsmatchingservice.users.service.UserService;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ApiDataResponse signup(@RequestBody UserPostDto userPostDto) {
        boolean result = userService.createUser(userPostDto);
        if (result == true) {
            return ApiDataResponse.of(ErrorCode.CREATED, result);
        } else {
            return ApiDataResponse.of(ErrorCode.INTERNAL_ERROR, null);
        }
    }

}
