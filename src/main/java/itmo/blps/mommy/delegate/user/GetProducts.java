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
import java.util.List;

import static org.camunda.spin.Spin.JSON;

@Component
@Named
@RequiredArgsConstructor
public class GetProducts implements JavaDelegate {
    private final DelegateAuthChecker delegateAuthChecker;
    private final ProductService productService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            delegateAuthChecker.checkUserAuth(delegateExecution);
            String name = String.valueOf(delegateExecution.getVariable("name"));
            int page = Integer.parseInt(String.valueOf(delegateExecution.getVariable("page")));
            int perPage = Integer.parseInt(String.valueOf(delegateExecution.getVariable("per_page")));
            List<ProductDTO> products = productService.suggestProducts(name, page, perPage);
            delegateExecution.setVariable("result", JSON(products).toString());

        } catch (Throwable throwable) {
            delegateExecution.setVariable("error", throwable.getMessage());
            throw new BpmnError("error", throwable.getMessage());
        }
    }
}
