package itmo.blps.mommy.validator;

import itmo.blps.mommy.repository.ProductRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class ProductValidator implements ConstraintValidator<ValidUser, Integer> {
    private final ProductRepository productRepository;

    @Override
    public boolean isValid(Integer productId, ConstraintValidatorContext constraintValidatorContext) {
        return productRepository.existsById(productId);
    }
}
