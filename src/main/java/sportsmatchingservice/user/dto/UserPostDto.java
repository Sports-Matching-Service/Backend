package sportsmatchingservice.user.dto;


import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class UserPostDto {

    @NotBlank(message = "이메일을 작성해주세요.")
    @Email(message = "이메일 형식에 맞게 작성해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;
}
