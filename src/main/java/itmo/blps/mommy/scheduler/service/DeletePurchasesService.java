package itmo.blps.mommy.scheduler.service;

import itmo.blps.mommy.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeletePurchasesService {

    @Value("${scheduler.delete.batch:1000}")
    private int BATCH;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public void deletePurchases() {
        List<Integer> ids;
        do {
            ids = purchaseRepository.getDeleted(BATCH);
            purchaseRepository.deleteAllById(ids);
        } while (!ids.isEmpty());
    }
}
