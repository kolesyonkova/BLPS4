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
@Table(name = "user_favorite_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFavoriteProduct {
    @EmbeddedId
    private UserProductKey id;

    @CreatedDate
    @Column(name = "date_created")
    private Instant dateCreated;

    public UserFavoriteProduct(Integer userId, Integer productId) {
        this.id = new UserProductKey(userId, productId);
    }
}
