package sportsmatchingservice.auth.service;

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

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sportsmatchingservice.auth.dto.OauthTokenDto;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OauthKakaoService {

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
    @Value("${social.kakao.path.tokenUrl")
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
}
