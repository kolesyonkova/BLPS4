package itmo.blps.mommy.service;

import itmo.blps.mommy.dto.PurchaseRequestDTO;
import itmo.blps.mommy.dto.PurchaseResponseDTO;
import itmo.blps.mommy.entity.Purchase;
import itmo.blps.mommy.entity.PurchaseStatus;
import itmo.blps.mommy.mapper.PurchaseMapper;
import itmo.blps.mommy.service.database.PurchaseDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PurchaseService {
    private PurchaseDbService purchaseDbService;
    private PurchaseMapper purchaseMapper;

    public List<PurchaseResponseDTO> suggestPurchases(String name, int page, int perPage) {
        List<Purchase> purchases = purchaseDbService.findPurchases(name, page, perPage);
        return purchases
                .stream()
                .map(purchaseMapper::toDto)
                .collect(Collectors.toList());
    }

    public PurchaseResponseDTO createPurchase(PurchaseRequestDTO purchaseRequestDTO) throws Exception {
        Purchase purchase = purchaseMapper.fromDto(purchaseRequestDTO);
        return purchaseMapper.toDto(purchaseDbService.create(purchase));
    }

    public PurchaseResponseDTO getPurchase(Integer purchaseId) throws Exception {
        return purchaseMapper.toDto(purchaseDbService.findById(purchaseId));
    }


    public void deletePurchase(Integer purchaseId) throws Exception {
        Purchase purchase = purchaseDbService.findById(purchaseId);

        if (purchase.getPurchaseStatus() == PurchaseStatus.PAID) {
            throw new RuntimeException("You can't delete paid purchase!");
        }
        purchaseDbService.delete(purchase);
    }
}
