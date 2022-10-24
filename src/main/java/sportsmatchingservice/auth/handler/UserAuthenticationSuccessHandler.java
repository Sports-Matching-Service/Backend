package sportsmatchingservice.auth.handler;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import sportsmatchingservice.auth.domain.User;
import sportsmatchingservice.constant.ErrorCode;
import sportsmatchingservice.constant.dto.ApiDataResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", user.getEmail());
        userInfo.put("nickname", user.getNickname());
        userInfo.put("id", user.getId());
        userInfo.put("roles", user.getRoles());

        ApiDataResponse dataResponse = ApiDataResponse.of(ErrorCode.OK, userInfo);
        Gson gson = new Gson();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(gson.toJson(dataResponse, ApiDataResponse.class));

        log.info("#Authenticated successfully");
    }
}
