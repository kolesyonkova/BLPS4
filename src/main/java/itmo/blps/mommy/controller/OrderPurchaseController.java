package itmo.blps.mommy.controller;

import itmo.blps.mommy.dto.OrderPurchaseDto;
import itmo.blps.mommy.entity.UserPurchase;
import itmo.blps.mommy.service.OrderPurchaseService;
import itmo.blps.mommy.validator.ValidProduct;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orderPurchase")
@AllArgsConstructor
public class OrderPurchaseController {
    private final OrderPurchaseService orderPurchaseService;

    @PostMapping("/create")
    public ResponseEntity<OrderPurchaseDto> addOrderPurchase(@RequestBody @Valid OrderPurchaseDto orderPurchaseDto) throws Exception {
        return ResponseEntity.ok(orderPurchaseService.addOrderPurchase(orderPurchaseDto));
    }

    @GetMapping("/purchases/{purchaseId}")
    public ResponseEntity<OrderPurchaseDto> getOrderPurchase(@PathVariable(name = "purchaseId") @Valid @ValidProduct Integer purchaseId) {
        return ResponseEntity.ok(orderPurchaseService.getOrderPurchase(purchaseId));
    }


    @DeleteMapping("/purchases/{purchaseId}")
    public ResponseEntity<?> deleteOrderPurchase(@PathVariable(name = "purchaseId") @Valid @ValidProduct Integer purchaseId) {
        orderPurchaseService.deleteOrderPurchase(purchaseId);
        return ResponseEntity.ok("Сущность выкупа успешно удалена!");
    }

    @GetMapping
    public ResponseEntity<List<UserPurchase>> getPagedOrderPurchase(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                    @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(orderPurchaseService.getPagedOrderPurchase(page, pageSize).getContent());
    }
}
