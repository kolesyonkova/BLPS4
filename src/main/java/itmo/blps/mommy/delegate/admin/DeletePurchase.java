package itmo.blps.mommy.delegate.admin;

import itmo.blps.mommy.dto.PurchaseResponseDTO;
import itmo.blps.mommy.mapper.PurchaseMapper;
import itmo.blps.mommy.service.DelegateAuthChecker;
import itmo.blps.mommy.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;

import static org.camunda.spin.Spin.JSON;

@Component
@Named
@RequiredArgsConstructor
public class DeletePurchase implements JavaDelegate {

    private final DelegateAuthChecker delegateAuthChecker;
    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            delegateAuthChecker.checkAdminAuth(delegateExecution);
            Integer purchaseId = Integer.valueOf(String.valueOf(delegateExecution.getVariable("purchaseId")));
            purchaseService.deletePurchase(purchaseId);
            delegateExecution.setVariable("result", "Покупка успешно удалена");

        } catch (Throwable throwable) {
            delegateExecution.setVariable("error", throwable.getMessage());
            throw new BpmnError("error", throwable.getMessage());
        }
    }
}
