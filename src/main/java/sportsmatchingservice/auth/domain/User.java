package sportsmatchingservice.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false, unique = true)
    private String nickname;

    @Setter
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Setter
    @Column
    private String password;

    @Setter
    @Column(nullable=false,
            columnDefinition = "boolean default false")
    private boolean isOauth;

    @Column(nullable = false, insertable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Setter
    @Column(nullable = false, insertable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    protected User(String email, String nickname, String phoneNumber, String password) {
        this.email = email;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public static User of(
            String email,
            String nickname,
            String phoneNumber,
            String password
    ) {
        return new User(email, nickname, phoneNumber, password);
    }

}