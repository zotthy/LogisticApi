package logistic.apilogistic.repository;

import logistic.apilogistic.entity.Cargo;
import logistic.apilogistic.entity.Cargo_owners;
import logistic.apilogistic.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo,Long> {
    Page<Cargo> findAll(Pageable pageable);
}
