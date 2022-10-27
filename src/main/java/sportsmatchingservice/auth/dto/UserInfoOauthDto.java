package sportsmatchingservice.auth.dto;

import lombok.*;
import sportsmatchingservice.auth.domain.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfoOauthDto {

    private String email;
    private String nickname;
    private String phoneNumber;

    private UserInfoOauthDto(String email, String nickname){
        this.email = email;
        this.nickname = nickname;
    }

    public User toEntity() {
        return User.of(
                this.email, this.nickname, this.phoneNumber
        );
    }

    static public UserInfoOauthDto of(){
        return new UserInfoOauthDto();
    }

    static public UserInfoOauthDto of(String email, String nickname, String phoneNumber) {
        return new UserInfoOauthDto(email, nickname, phoneNumber);
    }

    static public UserInfoOauthDto of(User user) {
        return new UserInfoOauthDto(user.getEmail(), user.getNickname(), user.getPhoneNumber());
    }

    static public UserInfoOauthDto of(String email, String nickname) {
        return new UserInfoOauthDto(email, nickname);
    }
}
