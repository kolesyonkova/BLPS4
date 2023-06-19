package itmo.blps.mommy.validator;

import itmo.blps.mommy.repository.UserRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UserValidator implements ConstraintValidator<ValidUser, Integer> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(Integer userId, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.existsById(userId);
    }
}
