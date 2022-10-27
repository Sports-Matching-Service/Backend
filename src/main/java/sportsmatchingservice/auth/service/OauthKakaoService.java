package sportsmatchingservice.auth.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@RequiredArgsConstructor
public class OauthKakaoService {

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

}
