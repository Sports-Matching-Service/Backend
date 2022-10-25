package sportsmatchingservice.auth.handler;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import sportsmatchingservice.auth.domain.User;
import sportsmatchingservice.auth.jwt.JwtTokenizer;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.constant.dto.ApiDataResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;

    public UserAuthenticationSuccessHandler(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        User user = (User) authentication.getPrincipal();

        String accessToken = delegateAccessToken(user);
        String refreshToken = delegateRefreshToken(user);

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", user.getEmail());
        userInfo.put("nickname", user.getNickname());
        userInfo.put("id", user.getId());
        userInfo.put("roles", user.getRoles());
        userInfo.put("access_token", "Bearer" + accessToken);
        userInfo.put("refresh_token", refreshToken);

        ApiDataResponse dataResponse = ApiDataResponse.of(ErrorCode.OK, userInfo);
        Gson gson = new Gson();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(gson.toJson(dataResponse, ApiDataResponse.class));

        log.info("#Authenticated successfully");
    }

    private String delegateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getEmail());
        claims.put("roles", user.getRoles());

        String subject = user.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(User user) {
        String subject = user.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }
}
