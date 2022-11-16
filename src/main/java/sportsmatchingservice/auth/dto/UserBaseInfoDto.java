package sportsmatchingservice.auth.dto;

import lombok.*;
import sportsmatchingservice.auth.domain.User;

@Data
@AllArgsConstructor
public class UserBaseInfoDto {

    private Long id;
    private String email;
    private String nickname;


    public static UserBaseInfoDto of(User user){
        return new UserBaseInfoDto(user.getId(), user.getEmail(), user.getNickname());
    }
}
