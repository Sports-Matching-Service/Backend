package sportsmatchingservice.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import sportsmatchingservice.auth.domain.Game;
import sportsmatchingservice.constant.Gender;
import sportsmatchingservice.constant.Sport;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class GamePostDto {

    @NotNull
    @JsonProperty("sport")
    private Sport sport;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startDateTime;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endDateTime;


    @NotNull
    @JsonProperty("address")
    private String address;

    @JsonProperty("addressDetail")
    private String addressDetail;

    @NotNull
    @JsonProperty("recruitment")
    private int recruitment;

    @NotNull
    @JsonProperty("minRecruitment")
    private int minRecruitment;

    @NotNull
    @JsonProperty("fee")
    private int fee;

    @NotNull
    @JsonProperty("gender")
    private Gender gender;

    public Game toEntity() {
        return Game.of(
                this.sport,
                this.startDateTime,
                this.endDateTime,
                this.address,
                this.addressDetail,
                this.recruitment,
                this.minRecruitment,
                this.fee,
                this.gender
        );
    }
}
