package logistic.apilogistic.repository;

import logistic.apilogistic.entity.MyDrivers;
import logistic.apilogistic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MyDriverRepository extends JpaRepository<MyDrivers,Long> {
    Page<MyDrivers> findByUser(Optional<User> user, Pageable pageable);
    boolean existsByUserIdAndDriverId(Long userId, Long driverId);
}
