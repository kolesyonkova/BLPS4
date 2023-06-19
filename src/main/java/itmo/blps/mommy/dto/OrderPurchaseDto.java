package itmo.blps.mommy.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderPurchaseDto {
    @NotNull
    private Integer purchaseId;
    @NotNull
    @Min(1)
    private Integer countOfProducts;
}
