package logistic.apilogistic.repository;

import logistic.apilogistic.entity.CargoAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoAddressRepository extends JpaRepository<CargoAddress,Long> {
}
