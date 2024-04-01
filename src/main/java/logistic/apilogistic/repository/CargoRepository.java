package logistic.apilogistic.repository;

import logistic.apilogistic.entity.Cargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CargoRepository extends PagingAndSortingRepository<Cargo,Long> {
    Page<Cargo> findAll(Pageable pageable);
    void save(Cargo cargo);
    Optional<Cargo> findById(Long id);

}
