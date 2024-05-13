package logistic.apilogistic.repository;

import logistic.apilogistic.entity.Cargo_owners;
import logistic.apilogistic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoOwnersRepository extends JpaRepository<Cargo_owners,Long> {
    List<Cargo_owners> findByUser(User user);
    Optional<Cargo_owners> findByUserAndCargoId(User user, Long cargoId);
}
