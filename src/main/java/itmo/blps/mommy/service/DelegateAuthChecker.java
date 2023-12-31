package itmo.blps.mommy.service;

import itmo.blps.mommy.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DelegateAuthChecker {
    private final JwtFilter jwtTokenFilter;

    public void checkAdminAuth(DelegateExecution delegateExecution) throws IllegalAccessException {
        checkRoleAuth(delegateExecution, List.of("ROLE_ADMIN"));
    }

    public void checkUserAuth(DelegateExecution delegateExecution) throws IllegalAccessException {
        checkRoleAuth(delegateExecution, List.of("ROLE_USER", "ROLE_ADMIN"));
    }

    private void checkRoleAuth(DelegateExecution delegateExecution, List<String> roles) throws IllegalAccessException {
        String userId = delegateExecution.getProcessEngineServices().getIdentityService().getCurrentAuthentication().getUserId();
        String token = TokenService.getUserToken(userId);
        if (token == null)
            throw new IllegalAccessException("Попробуйте авторизоваться еще раз. С вашим токеном что-то не так.");
        jwtTokenFilter.doFilter(token);
        List<String> userRoles = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (Collections.disjoint(userRoles, roles))
            throw new IllegalAccessException("У вас нет прав на этот процесс");
    }
}
