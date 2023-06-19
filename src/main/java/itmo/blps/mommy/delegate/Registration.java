package itmo.blps.mommy.delegate;


import itmo.blps.mommy.mapper.UserMapper;
import itmo.blps.mommy.service.TokenService;
import itmo.blps.mommy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.inject.Named;

@Component
@Named
@RequiredArgsConstructor
public class Registration implements JavaDelegate {
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        String userId = delegateExecution.getProcessEngineServices().getIdentityService().getCurrentAuthentication().getUserId();
        try {
            String email = String.valueOf(delegateExecution.getVariable("email"));
            String password = String.valueOf(delegateExecution.getVariable("password"));
            String token = userService.register(userMapper.toDto(email, password));
            delegateExecution.setVariable("result", token);
            TokenService.putUserToken(userId, token);
        } catch (Throwable throwable) {
            TokenService.putUserToken(userId, null);
            delegateExecution.setVariable("error", throwable.getMessage());
            throw new BpmnError("register_error", throwable.getMessage());
        }
    }
}


