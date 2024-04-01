package logistic.apilogistic.repository;

import logistic.apilogistic.entity.CargoAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoAddressRepository extends JpaRepository<CargoAddress,Long> {
}
