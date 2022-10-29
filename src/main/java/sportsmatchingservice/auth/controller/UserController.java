package sportsmatchingservice.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sportsmatchingservice.auth.dto.UserSignupDto;
import sportsmatchingservice.auth.dto.UserTokenDto;
import sportsmatchingservice.auth.service.OauthKakaoService;
import sportsmatchingservice.auth.service.OauthNaverService;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.constant.dto.ApiDataResponse;
import sportsmatchingservice.auth.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final OauthKakaoService oauthKakaoService;
    private final OauthNaverService oauthNaverService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
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
        } else if (social.equals("naver")) {
            return ApiDataResponse.of(ErrorCode.OK, oauthNaverService.getParameters());
        } else {
            return ApiDataResponse.of(ErrorCode.INTERNAL_ERROR, null);
        }
    }

    @RequestMapping("/oauth/tokens/{social}")
    public ApiDataResponse getUserTokenDto(@PathVariable("social") String social,
                                           @RequestParam String code,
                                           @RequestParam(required = false) String state) {
        if (social.equals("kakao")) {
            UserTokenDto userTokenDto = oauthKakaoService.getUserToken(code);
            return ApiDataResponse.of(ErrorCode.OK, userTokenDto);
        } else if (social.equals("naver")) {
            UserTokenDto userTokenDto = oauthNaverService.getUserToken(code, state);
            return ApiDataResponse.of(ErrorCode.OK, userTokenDto);
        } else {
            return ApiDataResponse.of(ErrorCode.INTERNAL_ERROR, null);
        }
    }
}
