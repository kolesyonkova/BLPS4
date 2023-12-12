package itmo.blps.mommy.service;

import itmo.blps.mommy.entity.Purchase;
import itmo.blps.mommy.entity.PurchaseStatus;
import itmo.blps.mommy.mapper.ProductMapper;
import itmo.blps.mommy.mapper.PurchaseMapper;
import itmo.blps.mommy.service.database.ProductDbService;
import itmo.blps.mommy.service.database.PurchaseDbService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PurchaseServiceTest {

    private static PurchaseDbService purchaseDbService;
    private static PurchaseMapper purchaseMapper;
    private static PurchaseService purchaseService;

    @BeforeAll
    public static void init() {
        purchaseDbService = Mockito.mock(ProductDbService.class);
        purchaseMapper = new PurchaseMapper(Mockito.mock(ProductDbService.class), new ProductMapper());
        purchaseService = new PurchaseService(purchaseDbService, purchaseMapper);
    }


    @Test
    public void paidPurchaseCanNotBeDeleted() throws Exception {
        int firstId = 1;
        int secondId = 2;
        Purchase firstPurchase = new Purchase(null, null, null, PurchaseStatus.CREATED, false);
        Purchase secndPurchase = new Purchase(null, null, null, PurchaseStatus.PAID, false);
        Mockito.when(purchaseDbService.findById(Mockito.eq(firstId))).thenReturn(firstPurchase);
        Mockito.when(purchaseDbService.findById(Mockito.eq(secondId))).thenReturn(secndPurchase);
        purchaseService.deletePurchase(firstId);
        assertThrows(RuntimeException.class, () -> purchaseService.deletePurchase(secondId));
    }
}
