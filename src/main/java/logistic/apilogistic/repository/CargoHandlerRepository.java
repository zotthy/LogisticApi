package logistic.apilogistic.repository;

import logistic.apilogistic.entity.Cargo_handler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoHandlerRepository extends JpaRepository<Cargo_handler,Long> {
}
