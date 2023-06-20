package itmo.blps.mommy.delegate.user;

import itmo.blps.mommy.dto.OrderPurchaseDto;
import itmo.blps.mommy.dto.PurchaseResponseDTO;
import itmo.blps.mommy.mapper.PurchaseMapper;
import itmo.blps.mommy.mapper.UserPurchaseMapper;
import itmo.blps.mommy.service.DelegateAuthChecker;
import itmo.blps.mommy.service.OrderPurchaseService;
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
public class CreateOrder implements JavaDelegate {

    private final DelegateAuthChecker delegateAuthChecker;
    private final OrderPurchaseService orderPurchaseService;
    private final UserPurchaseMapper userPurchaseMapper;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            delegateAuthChecker.checkUserAuth(delegateExecution);
            Integer purchaseId = Integer.valueOf(String.valueOf(delegateExecution.getVariable("purchaseId")));
            Integer countOfProducts = Integer.valueOf(String.valueOf(delegateExecution.getVariable("countOfProducts")));
            OrderPurchaseDto result = orderPurchaseService.addOrderPurchase(userPurchaseMapper.toDto(purchaseId, countOfProducts));
            delegateExecution.setVariable("result", JSON(result).toString());

        } catch (Throwable throwable) {
            delegateExecution.setVariable("error", throwable.getMessage());
            throw new BpmnError("error", throwable.getMessage());
        }
    }
}
