package ykvlv.lab4.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ykvlv.lab4.data.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
}
