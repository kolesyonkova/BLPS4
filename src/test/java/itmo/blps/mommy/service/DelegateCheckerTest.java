package itmo.blps.mommy.service;

import itmo.blps.mommy.config.jwt.JwtProvider;
import itmo.blps.mommy.filter.JwtFilter;
import itmo.blps.mommy.mapper.ProductMapper;
import itmo.blps.mommy.service.database.ProductDbService;
import org.camunda.bpm.engine.impl.pvm.runtime.ExecutionImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;

public class DelegateCheckerTest {

    private static JwtFilter jwtFilter;
    private static DelegateAuthChecker delegateChecker;

    @BeforeAll
    public static void init() {
        jwtFilter = Mockito.mock(JwtFilter.class);
        delegateChecker = new DelegateAuthChecker(jwtFilter);
    }



    @Test
    public void invalidTokenCanNotDelegate() {
        ExecutionImpl execution = new ExecutionImpl()
                .createExecution()
                .setStartContext(Map.of("token", "invalid-token", "userId", "id"));
        execution.initialize();
        assertThrows(IllegalAccessException.class, () -> delegateChecker.checkUserAuth(execution));
    }


    @Test
    public void nonSettedAuthenticationCanNotDelegate() {
        ExecutionImpl execution = new ExecutionImpl()
                .createExecution()
                .setStartContext(Map.of("token", "valid-token", "userId", "id"));
        execution.initialize();
        TokenService.putUserToken("id", "valid-token");
        assertThrows(IllegalAccessException.class, () -> delegateChecker.checkUserAuth(execution));
    }


    @Test
    public void authenticatedCanDelegate() throws IllegalAccessException {
        ExecutionImpl execution = new ExecutionImpl()
                .createExecution()
                .setStartContext(Map.of("token", "valid-token", "userId", "id"));
        execution.initialize();
        TokenService.putUserToken("id", "valid-token");
        doAnswer((Answer<Void>) invocation -> {
            SecurityContextHolder.getContext()
                    .setAuthentication(new TestingAuthenticationToken(null, null))
            return null;
        })
                .when(jwtFilter).doFilter(Mockito.eq("valid-token"));
        delegateChecker.checkUserAuth(execution);
    }

}
