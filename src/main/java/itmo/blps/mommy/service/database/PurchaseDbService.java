package itmo.blps.mommy.service.database;

import itmo.blps.mommy.entity.Purchase;
import itmo.blps.mommy.entity.PurchaseStatus;
import itmo.blps.mommy.repository.PurchaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseDbService {
    private final PurchaseRepository purchaseRepository;

    @Transactional(readOnly = true)
    public Purchase findById(Integer purchaseId) throws Exception {
        return purchaseRepository.findById(purchaseId).orElseThrow(() -> new Exception("Выкупа с существующим айди не найдено"));
    }

    @Transactional(readOnly = true)
    public List<Purchase> findPurchases(String name, int page, int perPage) {
        return purchaseRepository.findPurchases(
                name.strip(), perPage, (page - 1) * perPage, PurchaseStatus.CREATED.name()
        );
    }

    @Transactional
    public Purchase create(Purchase purchase) throws Exception {
        return purchaseRepository.save(purchase);
    }

    @Transactional
    public void delete(Purchase purchase) throws Exception {
        purchaseRepository.delete(purchase);
    }
}
