package itmo.blps.mommy.repository;

import itmo.blps.mommy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String username);

}
