package itmo.blps.mommy.delegate.admin;


import itmo.blps.mommy.dto.ProductDTO;
import itmo.blps.mommy.mapper.ProductMapper;
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
public class UpdateProduct implements JavaDelegate {
    private final DelegateAuthChecker delegateAuthChecker;
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        try {
            delegateAuthChecker.checkAdminAuth(delegateExecution);
            Integer id = Integer.valueOf(String.valueOf(delegateExecution.getVariable("id")));
            String name = String.valueOf(delegateExecution.getVariable("name"));
            Float weight = Float.valueOf(String.valueOf(delegateExecution.getVariable("weight")).replaceAll(",", "."));
            String consumerInfo = String.valueOf(delegateExecution.getVariable("consumerInfo"));
            ProductDTO productDtoResult = productService.updateProduct(productMapper.toDto(id, name, weight, consumerInfo));
            delegateExecution.setVariable("result", JSON(productDtoResult).toString());

        } catch (Throwable throwable) {
            delegateExecution.setVariable("error", throwable.getMessage());
            throw new BpmnError("error", throwable.getMessage());
        }
    }
}
