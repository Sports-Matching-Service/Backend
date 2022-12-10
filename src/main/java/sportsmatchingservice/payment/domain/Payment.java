package sportsmatchingservice.payment.domain;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import sportsmatchingservice.constant.PaymentStatus;
import sportsmatchingservice.constant.Sport;
import sportsmatchingservice.game.domain.Participation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue
    private String partnerOrderId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "PARTICIPATION_ID")
    private Participation participation;

    @Column(nullable = false)
    private String partnerUserId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Sport itemName;

    @Column
    private String itemCode;

    @Column(nullable = false, columnDefinition = "int default 1")
    private int quantity;

    @Column(nullable = false)
    private int totalAmount;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int taxFreeAmount;

    @Column
    private int vatAmount;

    @Column
    private String tid;

    @Column
    private String aid;

    @OneToOne
    @JoinColumn(name = "AMOUNT_ID")
    private Amount amount;

    @OneToOne
    @JoinColumn(name = "CARD_INFO_ID")
    private CardInfo cardInfo;

    @Column
    private PaymentStatus status;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime approvedAt;

    @Column
    private LocalDateTime canceledAt;

    @Column(nullable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
