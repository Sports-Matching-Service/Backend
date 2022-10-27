package sportsmatchingservice.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sportsmatchingservice.auth.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class UserSignupDto {

    @NotBlank(message = "이메일을 작성해주세요.")
    @Email(message = "이메일 형식에 맞게 작성해주세요.")
    String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    String nickname;

    @NotBlank(message = "전화번호를 입력해주세요.")
    String phoneNumber;

    public User toEntity() {
        return User.of(
                this.email,
                this.nickname,
                this.phoneNumber,
                this.password
        );
    }

    public static UserSignupDto of(User user){
        return new UserSignupDto(
                user.getEmail(),
                user.getPassword(),
                user.getNickname(),
                user.getPhoneNumber()
        );
    }
}
