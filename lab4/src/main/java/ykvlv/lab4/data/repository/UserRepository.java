package ykvlv.lab4.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ykvlv.lab4.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
