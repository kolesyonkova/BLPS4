package itmo.blps.mommy.mapper;

import itmo.blps.mommy.dto.PurchaseRequestDTO;
import itmo.blps.mommy.dto.PurchaseResponseDTO;
import itmo.blps.mommy.entity.Product;
import itmo.blps.mommy.entity.Purchase;
import itmo.blps.mommy.entity.PurchaseStatus;
import itmo.blps.mommy.service.database.ProductDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PurchaseMapper {
    private ProductDbService productDbService;
    private ProductMapper productMapper;

    public Purchase fromDto(PurchaseRequestDTO purchaseRequestDTO) {
        Product product = productDbService.findProduct(purchaseRequestDTO.getProductId());
        return new Purchase(
                purchaseRequestDTO.getMinCount(),
                0,
                product,
                PurchaseStatus.CREATED,
                false
        );
    }

    public PurchaseResponseDTO toDto(Purchase purchase) {
        return new PurchaseResponseDTO(
                purchase.getId(),
                purchase.getMinCount(),
                purchase.getCurCount(),
                purchase.getPurchaseStatus(),
                productMapper.toDto(purchase.getProduct()),
                purchase.getIsDelited()
        );
    }

}
