package logistic.apilogistic.repository;

import logistic.apilogistic.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepozitory extends JpaRepository<UserRole,Long> {
    Optional<UserRole> findByName(String name);
}
