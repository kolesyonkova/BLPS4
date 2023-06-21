package itmo.blps.mommy.delegate.user;


import itmo.blps.mommy.dto.ProductDTO;
import itmo.blps.mommy.service.DelegateAuthChecker;
import itmo.blps.mommy.service.ProductService;
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
public class GetProduct implements JavaDelegate {
    private final DelegateAuthChecker delegateAuthChecker;
    private final ProductService productService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            delegateAuthChecker.checkUserAuth(delegateExecution);
            Integer id = Integer.valueOf(String.valueOf(delegateExecution.getVariable("id")));
            ProductDTO productDtoResult = productService.getProduct(id);
            delegateExecution.setVariable("result", JSON(productDtoResult).toString());

        } catch (Throwable throwable) {
            delegateExecution.setVariable("error", throwable.getMessage());
            throw new BpmnError("error", throwable.getMessage());
        }
    }
}
