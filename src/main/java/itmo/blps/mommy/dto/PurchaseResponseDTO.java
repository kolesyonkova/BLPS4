package itmo.blps.mommy.dto;

import itmo.blps.mommy.entity.PurchaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseResponseDTO {

    private Integer id;

    private Integer minCount;

    private Integer curCount;

    private PurchaseStatus status;

    private ProductDTO product;

    private Boolean isDeleted;

}
