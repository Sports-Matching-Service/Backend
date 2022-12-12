package sportsmatchingservice.payment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Amount {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private int total;
    @Column
    private int taxFree;
    @Column
    private int vat;
    @Column
    private int point;
    @Column
    private int discount;
    @Column
    private int greenDeposit;
}
