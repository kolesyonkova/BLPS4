//package itmo.blps.mommy.delegate;
//
//import black.orange.rutube.converter.UserConverter;
//import black.orange.rutube.security.TokenService;
//import black.orange.rutube.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.camunda.bpm.engine.delegate.BpmnError;
//import org.camunda.bpm.engine.delegate.DelegateExecution;
//import org.camunda.bpm.engine.delegate.JavaDelegate;
//import org.springframework.stereotype.Component;
//
//import javax.inject.Named;
//
//@Component
//@Named
//@RequiredArgsConstructor
//public class Auth implements JavaDelegate {
//    private final UserService userService;
//    private final UserConverter userConverter;
//
//    @Override
//    public void execute(DelegateExecution delegateExecution) {
//        String userId = delegateExecution.getProcessEngineServices().getIdentityService().getCurrentAuthentication().getUserId();
//        try {
//            String email = String.valueOf(delegateExecution.getVariable("email"));
//            String password = String.valueOf(delegateExecution.getVariable("password"));
//            String token = userService.auth(userConverter.toDto(email, password));
//            delegateExecution.setVariable("result", token);
//            TokenService.putUserToken(userId, token);
//        } catch (Throwable throwable) {
//            TokenService.putUserToken(userId, null);
//            delegateExecution.setVariable("error", throwable.getMessage());
//            throw new BpmnError("auth_error", throwable.getMessage());
//        }
//    }
//}
//
//
