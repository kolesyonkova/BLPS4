package itmo.blps.mommy.delegate.admin;

import itmo.blps.mommy.dto.ProductDTO;
import itmo.blps.mommy.dto.PurchaseResponseDTO;
import itmo.blps.mommy.mapper.PurchaseMapper;
import itmo.blps.mommy.service.DelegateAuthChecker;
import itmo.blps.mommy.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

import static org.camunda.spin.Spin.JSON;

@Component
@Named
@RequiredArgsConstructor
public class CreatePurchase implements JavaDelegate {

    private final DelegateAuthChecker delegateAuthChecker;
    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            delegateAuthChecker.checkAdminAuth(delegateExecution);
            Integer productId = Integer.valueOf(String.valueOf(delegateExecution.getVariable("productId")));
            Integer minCount = Integer.valueOf(String.valueOf(delegateExecution.getVariable("minCount")));
            PurchaseResponseDTO result = purchaseService.createPurchase(purchaseMapper.toDto(productId, minCount));
            delegateExecution.setVariable("result", JSON(result).toString());

        } catch (Throwable throwable) {
            delegateExecution.setVariable("error", throwable.getMessage());
            throw new BpmnError("error", throwable.getMessage());
        }
    }
}
