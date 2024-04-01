package logistic.apilogistic.repository;

import logistic.apilogistic.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRoleRepozitory extends JpaRepository<UserRole,Long> {
    Optional<UserRole> findByName(String name);
}
