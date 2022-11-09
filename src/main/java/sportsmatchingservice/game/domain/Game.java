package sportsmatchingservice.game.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import sportsmatchingservice.auth.domain.User;
import sportsmatchingservice.constant.Gender;
import sportsmatchingservice.constant.Sport;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "HOST_ID")
    private User host;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Sport sport;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private String address;

    @Column
    private String addressDetail;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private int recruitment;

    @Column(nullable = false)
    private int minRecruitment;

    @Column(nullable = false)
    private int currentRecruitment;

    @Column(nullable = false)
    private int fee;

    @Column(nullable = false, insertable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    private String info;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    protected Game(
            Sport sport,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            String address,
            int recruitment,
            int minRecruitment,
            int fee,
            Gender gender
    ) {
        this.sport = sport;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.address = address;
        this.recruitment = recruitment;
        this.minRecruitment = minRecruitment;
        this.fee = fee;
        this.gender = gender;
    }

    public static Game of(
            Sport sport,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            String address,
            String addressDetail,
            int recruitment,
            int minRecruitment,
            int fee,
            Gender gender,
            String info
    ) {
        Game game = new Game(sport, startDateTime, endDateTime, address, recruitment, minRecruitment, fee, gender);

        if (addressDetail != null) game.setAddressDetail(addressDetail);
        if (info != null && !info.isBlank()) game.setInfo(info);

        return game;
    }


}