package logistic.apilogistic.repository;

import logistic.apilogistic.Dtos.CargoDto;
import logistic.apilogistic.entity.Cargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CargoRepository extends JpaRepository<Cargo,Long> {
    Page<Cargo> findAll(Pageable pageable);
    @Query("SELECT c FROM Cargo c WHERE c.loadAddress.province = :province")
    List<CargoDto> findByLoadAddressProvince(@Param("province") String province);
}
