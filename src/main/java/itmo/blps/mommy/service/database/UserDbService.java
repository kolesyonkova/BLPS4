package itmo.blps.mommy.service.database;

import itmo.blps.mommy.entity.User;
import itmo.blps.mommy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserDbService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean existsById(Integer userId) throws Exception {
        return userRepository.existsById(userId);
    }

    @Transactional(readOnly = true)
    public User getById(Integer userId) throws Exception {
        return userRepository.getById(userId);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }
}
