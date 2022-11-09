package sportsmatchingservice.game.dto;

import lombok.*;
import sportsmatchingservice.auth.domain.User;
import sportsmatchingservice.auth.dto.UserBaseInfoDto;
import sportsmatchingservice.constant.Gender;
import sportsmatchingservice.constant.Sport;
import sportsmatchingservice.game.domain.Game;

import java.time.LocalDateTime;

@Setter
@Data
@NoArgsConstructor
public class GameResponseDto {
    private Long id;
    private UserBaseInfoDto host;
    private Sport sport;
    private Gender gender;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime deadline;
    private String address;
    private String addressDetail;
    private int recruitment;
    private int minRecruitment;
    private int currentRecruitment;
    private int fee;
    private String info;


    private GameResponseDto(
            Long id,
            User host,
            Sport sport,
            Gender gender,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            LocalDateTime deadline,
            String address,
            String addressDetail,
            int recruitment,
            int minRecruitment,
            int currentRecruitment,
            int fee,
            String info
    ){
        this.id = id;
        this.host = UserBaseInfoDto.of(host);
        this.sport = sport;
        this.gender = gender;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.deadline = deadline;
        this.address = address;
        this.addressDetail = addressDetail;
        this.recruitment = recruitment;
        this.minRecruitment = minRecruitment;
        this.currentRecruitment = currentRecruitment;
        this.fee = fee;
        this.info = info;
    }


    static public GameResponseDto of(Game game) {
        return new GameResponseDto(
                game.getId(),
                game.getHost(),
                game.getSport(),
                game.getGender(),
                game.getStartDateTime(),
                game.getEndDateTime(),
                game.getDeadline(),
                game.getAddress(),
                game.getAddressDetail(),
                game.getRecruitment(),
                game.getMinRecruitment(),
                game.getCurrentRecruitment(),
                game.getFee(),
                game.getInfo()
        );
    }



}

