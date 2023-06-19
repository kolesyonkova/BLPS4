package itmo.blps.mommy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "user_purchase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPurchase {
    @EmbeddedId
    private UserPurchaseKey id;

    @CreatedDate
    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "products_count")
    private Integer productsCount;

    public UserPurchase(Integer userId, Integer purchaseId) {
        this.id = new UserPurchaseKey(userId, purchaseId);
    }
}
