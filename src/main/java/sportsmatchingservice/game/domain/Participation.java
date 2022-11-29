package sportsmatchingservice.game.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import sportsmatchingservice.auth.domain.User;
import sportsmatchingservice.game.domain.Game;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"game_id", "user_id"})
})
public class Participation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "GAME_ID")
    private Game game;

    @ManyToOne
    @JoinColumn(nullable = false, name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private boolean isProgressed;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime deletedAt;
}
