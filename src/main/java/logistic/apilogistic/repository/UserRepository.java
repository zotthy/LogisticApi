package logistic.apilogistic.repository;

import logistic.apilogistic.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String emial);

    User getUserByEmail(String userEmail);
}
