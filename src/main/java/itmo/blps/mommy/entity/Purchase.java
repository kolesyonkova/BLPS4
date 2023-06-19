package itmo.blps.mommy.entity;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Table(name = "purchases")
@Data
@TypeDef(name = "purchase_status", typeClass = PurchaseStatusEnumType.class)
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "min_count")
    private Integer minCount;

    @Column(name = "cur_count")
    private Integer curCount;

    @Column(name = "is_deleted")
    private Boolean isDelited;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Type(type = "purchase_status")
    private PurchaseStatus purchaseStatus;

    public Purchase(Integer minCount, Integer curCount, Product product, PurchaseStatus purchaseStatus, Boolean isDeleted) {
        this.minCount = minCount;
        this.curCount = curCount;
        this.product = product;
        this.purchaseStatus = purchaseStatus;
        this.isDelited = isDeleted;
    }

    public Purchase() {
    }
}
