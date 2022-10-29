package sportsmatchingservice.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sportsmatchingservice.auth.dto.OauthTokenDto;
import sportsmatchingservice.auth.jwt.JwtTokenizer;
import sportsmatchingservice.auth.repository.UserRepository;
import sportsmatchingservice.auth.utils.CustomAuthorityUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

@Service
public class OauthNaverService {
    private final UserRepository userRepository;
    private final CustomAuthorityUtils authorityUtils;
    private final JwtTokenizer jwtTokenizer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Getter
    @Value("${social.naver.params.clientId}")
    private String clientId;

    @Getter
    @Value("${social.naver.params.clientSecret}")
    private String clientSecret;

    @Getter
    @Value("${social.naver.path.redirectUri}")
    private String redirectUri;

    @Getter
    @Value("${social.naver.path.userInfoUrl}")
    private String userInfoUrl;

    @Getter
    @Value("${social.naver.path.tokenUrl}")
    private String tokenUrl;

    public OauthNaverService(UserRepository userRepository, CustomAuthorityUtils authorityUtils, JwtTokenizer jwtTokenizer) {
        this.userRepository = userRepository;
        this.authorityUtils = authorityUtils;
        this.jwtTokenizer = jwtTokenizer;
    }

    public HashMap<String, String> getParameters() {
        HashMap<String, String> params = new HashMap<>();
        params.put("clientId", getClientId());
        params.put("redirectUri", getRedirectUri());
        params.put("state", generateState());
        return params;
    }

    public String getAccessToken(String authorizationCode, String state) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("grant_type", "authorization_code");
        params.set("client_id", getClientId());
        params.set("client_secret", getClientSecret());
        params.set("code", authorizationCode);
        params.set("state", state);

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


    public String generateState() {
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString();
        return state;
    }
}
