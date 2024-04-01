package logistic.apilogistic.repository;

import logistic.apilogistic.entity.Cargo_handler;
import logistic.apilogistic.entity.Cargo_owners;
import logistic.apilogistic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoHandlerRepository extends JpaRepository<Cargo_handler,Long> {
    List<Cargo_handler> findByUser(User user);
}
