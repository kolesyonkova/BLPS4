package itmo.blps.mommy.scheduler.service;

import itmo.blps.mommy.repository.PurchaseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class DeletePurchasesService {

    @Value("${scheduler.delete.batch:1000}")
    private int BATCH;

    private PurchaseRepository purchaseRepository;

    public void deletePurchases() {
        List<Integer> ids;
        do {
            ids = purchaseRepository.getDeleted(BATCH);
            purchaseRepository.deleteAllById(ids);
            log.info("{} purchases deleted", ids.size());
        } while (!ids.isEmpty());
    }
}
