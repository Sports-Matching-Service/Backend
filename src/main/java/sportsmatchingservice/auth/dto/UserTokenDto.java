package sportsmatchingservice.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sportsmatchingservice.auth.domain.User;

@Getter
@NoArgsConstructor
public class UserTokenDto {

    private Long id;
    private String email;
    private String nickname;

    @Setter
    @JsonProperty("access_token")
    private String accessToken;

    @Setter
    @JsonProperty("refresh_token")
    private String refreshToken;

    private UserTokenDto(Long id, String email, String nickname){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    static public UserTokenDto of(User user) {
        return new UserTokenDto(user.getId(), user.getEmail(), user.getNickname());
    }

    static public UserTokenDto of(){
        return new UserTokenDto();
    }

}
