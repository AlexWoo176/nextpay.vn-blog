package nextpay.vn.blog.repository;

import nextpay.vn.blog.entity.role.Role;
import nextpay.vn.blog.entity.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}