package itmo.blps.mommy.delegate.user;

import itmo.blps.mommy.dto.OrderPurchaseDto;
import itmo.blps.mommy.dto.PurchaseResponseDTO;
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

import java.util.List;

import static org.camunda.spin.Spin.JSON;

@Component
@Named
@RequiredArgsConstructor
public class FindPurchases implements JavaDelegate {

    private final DelegateAuthChecker delegateAuthChecker;
    private final PurchaseService PurchaseService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            delegateAuthChecker.checkUserAuth(delegateExecution);
            String name = String.valueOf(delegateExecution.getVariable("name"));
            int page = Integer.parseInt(String.valueOf(delegateExecution.getVariable("page")));
            int perPage = Integer.parseInt(String.valueOf(delegateExecution.getVariable("perPage")));
            List<PurchaseResponseDTO> result = PurchaseService.suggestPurchases(name, page, perPage);
            delegateExecution.setVariable("result", JSON(result).toString());

        } catch (Throwable throwable) {
            delegateExecution.setVariable("error", throwable.getMessage());
            throw new BpmnError("error", throwable.getMessage());
        }
    }
}
