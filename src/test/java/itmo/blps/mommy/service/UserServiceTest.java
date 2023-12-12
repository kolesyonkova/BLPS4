package itmo.blps.mommy.service;

import itmo.blps.mommy.config.jwt.JwtProvider;
import itmo.blps.mommy.dto.UserDTO;
import itmo.blps.mommy.entity.Role;
import itmo.blps.mommy.entity.User;
import itmo.blps.mommy.exception.AuthException;
import itmo.blps.mommy.mapper.UserMapper;
import itmo.blps.mommy.repository.RoleRepository;
import itmo.blps.mommy.service.database.UserDbService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserServiceTest {

    private static UserDbService userDbService;
    private static RoleRepository roleRepository;
    private static JwtProvider jwtProvider;
    private static UserService userService;

    @BeforeAll
    public static void init() {
        userDbService = Mockito.mock(UserDbService.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        jwtProvider = new JwtProvider(Mockito.mock(UserDetailsService.class));
        userService = new UserService(userDbService, new BCryptPasswordEncoder(),
                new UserMapper(roleRepository), jwtProvider);
    }


    @Test
    public void userCanRegister() {
        String email = "email@domain.com";
        String password = "password";
        Role role = new Role("role");
        UserDTO userDTO = new UserDTO(email, password);
        Mockito.when(roleRepository.findByName(Mockito.any())).thenReturn(role);
        Mockito.when(userDbService.create(Mockito.any())).thenReturn(new User(email, password, role));
        userService.register(userDTO);
    }


    @Test
    public void userCanNotRegisterWithDuplicateEmail() {
        String email = "email@domain.com";
        String password = "password";
        Role role = new Role("role");
        UserDTO userDTO = new UserDTO(email, password);
        Mockito.when(roleRepository.findByName(Mockito.any())).thenReturn(role);
        Mockito.when(userDbService.findByEmail(Mockito.eq(email))).thenReturn(new User(email, password, role));
        assertThrows(AuthException.class, () -> userService.register(userDTO));
    }


    @Test
    public void userCanAuth() {
        String email = "email@domain.com";
        String password = "password";
        Role role = new Role("role");
        UserDTO userDTO = new UserDTO(email, password);
        Mockito.when(userDbService.findByEmail(Mockito.eq(email))).thenReturn(new User(email, password, role));
        userService.auth(userDTO);
    }

}
