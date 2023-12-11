package itmo.blps.mommy.mapper;


import itmo.blps.mommy.dto.UserDTO;
import itmo.blps.mommy.entity.User;
import itmo.blps.mommy.enums.Roles;
import itmo.blps.mommy.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private RoleRepository roleRepository;

    public UserDTO toDto(User user) {
        return new UserDTO(user.getEmail(), user.getPassword());
    }

    public UserDTO toDto(String email, String password) {
        return new UserDTO(email, password);
    }

    public User fromDto(UserDTO userDTO) {
        return new User(
                userDTO.getEmail(),
                userDTO.getPassword(),
                roleRepository.findByName(Roles.USER.getName())
        );
    }

}
