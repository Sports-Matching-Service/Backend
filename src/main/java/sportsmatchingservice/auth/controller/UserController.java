package sportsmatchingservice.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sportsmatchingservice.auth.dto.UserSignupDto;
import sportsmatchingservice.auth.service.OauthKakaoService;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.constant.dto.ApiDataResponse;
import sportsmatchingservice.auth.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final OauthKakaoService oauthKakaoService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public ApiDataResponse signup(@RequestBody UserSignupDto userSignupDto) {
        boolean result = userService.createUser(userSignupDto);
        if (result == true) {
            return ApiDataResponse.of(ErrorCode.CREATED, result);
        } else {
            return ApiDataResponse.of(ErrorCode.INTERNAL_ERROR, null);
        }
    }

    @GetMapping("/oauth/params/{social}")
    public ApiDataResponse getOauthParams(@PathVariable("social") String social) {
        if (social.equals("kakao")){
            return ApiDataResponse.of(ErrorCode.OK, oauthKakaoService.getParameters());
        } else {
            return ApiDataResponse.of(ErrorCode.INTERNAL_ERROR, null);
        }
    }
}
