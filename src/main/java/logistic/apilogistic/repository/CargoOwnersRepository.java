package logistic.apilogistic.repository;

import logistic.apilogistic.entity.Cargo_owners;
import logistic.apilogistic.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CargoOwnersRepository extends JpaRepository<Cargo_owners,Long> {
    List<Cargo_owners> findByUser(User user);
}
