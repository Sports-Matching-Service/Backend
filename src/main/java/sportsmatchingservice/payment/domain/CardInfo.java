package sportsmatchingservice.payment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CardInfo {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String purchaseCorp;
    @Column
    private String purchaseCorpCode;
    @Column
    private String issuerCorp;
    @Column
    private String issuerCorpCode;
    @Column
    private String kakaopayPurchaseCorp;
    @Column
    private String kakaopayPurchaseCorpCode;
    @Column
    private String kakaopayIssuerCorp;
    @Column
    private String kakaopayIssuerCorpCode;
    @Column
    private String bin;
    @Column
    private String cardType;
    @Column
    private String installMonth;
    @Column
    private String approvedId;
    @Column
    private String cardMin;
    @Column
    private String interestFreeInstall;
    @Column
    private String cardItemCode;
}
