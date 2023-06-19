package itmo.blps.mommy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class PurchaseRequestDTO {

    @NonNull
    @JsonProperty(namespace = "minCount", required = true)
    private Integer minCount;

    @NonNull
    @JsonProperty(namespace = "productId", required = true)
    private Integer productId;
}
