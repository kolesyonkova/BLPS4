package itmo.blps.mommy.controller;

import itmo.blps.mommy.dto.PurchaseRequestDTO;
import itmo.blps.mommy.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping("/purchases")
    public ResponseEntity<?> getPurchases(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "per_page", required = false, defaultValue = "20") Integer perPage
    ) {
        return ResponseEntity.ok(purchaseService.suggestPurchases(name, page, perPage));
    }

    @PostMapping("/admin/purchases")
    public ResponseEntity<?> createPurchase(@RequestBody PurchaseRequestDTO purchaseRequestDTO) throws Exception {
        return ResponseEntity.ok(purchaseService.createPurchase(purchaseRequestDTO));
    }

    @DeleteMapping("/admin/purchases/{id}")
    public ResponseEntity<?> deletePurchase(@PathVariable int id) throws Exception {
        purchaseService.deletePurchase(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/purchases/{id}")
    public ResponseEntity<?> getPurchase(@PathVariable int id) throws Exception {
        return ResponseEntity.ok(purchaseService.getPurchase(id));
    }

}
