package itmo.blps.mommy.delegate.user;

import itmo.blps.mommy.dto.OrderPurchaseDto;
import itmo.blps.mommy.entity.UserPurchase;
import itmo.blps.mommy.mapper.UserPurchaseMapper;
import itmo.blps.mommy.service.DelegateAuthChecker;
import itmo.blps.mommy.service.OrderPurchaseService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;

import java.util.List;

import static org.camunda.spin.Spin.JSON;

@Component
@Named
@RequiredArgsConstructor
public class GetOrders implements JavaDelegate {

    private final DelegateAuthChecker delegateAuthChecker;
    private final OrderPurchaseService orderPurchaseService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            delegateAuthChecker.checkUserAuth(delegateExecution);
            List<UserPurchase> result = orderPurchaseService.getMyOrders();
            delegateExecution.setVariable("result", result.toString());

        } catch (Throwable throwable) {
            delegateExecution.setVariable("error", throwable.getMessage());
            throw new BpmnError("error", throwable.getMessage());
        }
    }
}
