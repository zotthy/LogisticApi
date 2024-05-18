package logistic.apilogistic.repository;

import logistic.apilogistic.entity.Cargo_handler;
import logistic.apilogistic.entity.Cargo_owners;
import logistic.apilogistic.entity.Driver;
import logistic.apilogistic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoHandlerRepository extends JpaRepository<Cargo_handler,Long> {
    List<Cargo_handler> findByUser(User user);
    List<Cargo_handler> findByDriver(Driver driver);
    Optional<Cargo_handler> findByCargoId(Long cargoId);
    @Query("SELECT ch FROM Cargo_handler ch WHERE ch.driver = :driver AND ch.cargo.status = :status")
    List<Cargo_handler> findActiveCargosByDriver(@Param("driver") Driver driver, @Param("status") String status);
}
