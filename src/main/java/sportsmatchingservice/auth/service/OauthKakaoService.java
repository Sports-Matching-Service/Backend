package sportsmatchingservice.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sportsmatchingservice.auth.domain.User;
import sportsmatchingservice.auth.dto.OauthTokenDto;
import sportsmatchingservice.auth.dto.UserInfoOauthDto;
import sportsmatchingservice.auth.dto.UserTokenDto;
import sportsmatchingservice.auth.jwt.JwtTokenizer;
import sportsmatchingservice.auth.repository.UserRepository;
import sportsmatchingservice.auth.utils.CustomAuthorityUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OauthKakaoService {

    private final UserRepository userRepository;
    private final CustomAuthorityUtils authorityUtils;
    private final JwtTokenizer jwtTokenizer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Getter
    @Value("${social.kakao.params.clientId}")
    private String clientId;

    @Getter
    @Value("${social.kakao.params.clientSecret}")
    private String clientSecret;

    @Getter
    @Value("${social.kakao.path.redirectUri}")
    private String redirectUri;

    @Getter
    @Value("${social.kakao.path.userInfoUrl}")
    private String userInfoUrl;

    @Getter
    @Value("${social.kakao.path.tokenUrl}")
    private String tokenUrl;


    public HashMap<String, String> getParameters() {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("clientId", getClientId());
        parameters.put("redirectUri", getRedirectUri());
        return parameters;
    }

    public String getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("grant_type", "authorization_code");
        params.set("client_id", getClientId());
        params.set("redirect_uri", getRedirectUri());
        params.set("code", code);
        params.set("client_secret", getClientSecret());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate
                .postForEntity(getTokenUrl(), request, String.class);

        try {
            return objectMapper.readValue(response.getBody(), OauthTokenDto.class).getAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserInfoOauthDto getUserInfo(String accessToken)  {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        String response = restTemplate
                .postForEntity(getUserInfoUrl(), request, String.class).getBody();

        try {
            JsonNode jsonNode = objectMapper.readTree(String.valueOf(response));
            String email = String.valueOf(jsonNode.get("kakao_account").get("email").asText());
            String nickname = String.valueOf(jsonNode.get("kakao_account").get("profile").get("nickname").asText());
//            String phoneNumber = String.valueOf(jsonNode.get("kakao_account").get("phone_number").asText());

            return UserInfoOauthDto.of(email, nickname);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return UserInfoOauthDto.of();
    }

    @Transactional
    public UserTokenDto getUserToken(String authorizationCode){
        String kakaoAccessToken = "";

        kakaoAccessToken = getAccessToken(authorizationCode);
        UserInfoOauthDto userInfoOauthDto = getUserInfo(kakaoAccessToken);
        Optional<User> optional = userRepository.findByEmail(
                userInfoOauthDto.getEmail());

        if (optional.isPresent()) {
            User user = optional.get();
            UserTokenDto userTokenDto = UserTokenDto.of(user);
            setTokens(user, userTokenDto);
            return userTokenDto;
        } else {
            User user = userInfoOauthDto.toEntity();
            List<String> roles = authorityUtils.createRoles(user.getEmail());
            user.setRoles(roles);
            UserTokenDto userTokenDto = UserTokenDto.of(userRepository.save(user));
            setTokens(user, userTokenDto);
            return userTokenDto;
        }
    }

    protected void setTokens(User user, UserTokenDto userTokenDto) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("username", user.getEmail());
        claims.put("roles", user.getRoles());
        String subject = user.getEmail();
        Date accessTokenExpiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        Date refreshTokenExpiration= jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, accessTokenExpiration, base64EncodedSecretKey);
        String refreshToken = jwtTokenizer.generateRefreshToken(subject, refreshTokenExpiration, base64EncodedSecretKey);

        userTokenDto.setAccessToken(accessToken);
        userTokenDto.setRefreshToken(refreshToken);
    }

}
