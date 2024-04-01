package logistic.apilogistic.service;

import jakarta.persistence.EntityNotFoundException;
import logistic.apilogistic.Dtos.CargoDto;
import logistic.apilogistic.config.JwtService;
import logistic.apilogistic.dtoMapper.CargoMapper;
import logistic.apilogistic.entity.Cargo;
import logistic.apilogistic.entity.Cargo_handler;
import logistic.apilogistic.entity.Cargo_owners;
import logistic.apilogistic.entity.User;
import logistic.apilogistic.repository.CargoHandlerRepository;
import logistic.apilogistic.repository.CargoRepository;
import logistic.apilogistic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CargoRepository cargoRepository;
    private final CargoHandlerRepository cargoHandlerRepository;
    private final CargoMapper cargoMapper;

    @Autowired
    public OrdersService(JwtService jwtService, UserRepository userRepository,
                         CargoRepository cargoRepository, CargoHandlerRepository cargoHandlerRepository,
                         CargoMapper cargoMapper) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.cargoRepository = cargoRepository;
        this.cargoHandlerRepository = cargoHandlerRepository;
        this.cargoMapper = cargoMapper;
    }

    public Page<CargoDto> getCargosByUser(String token, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String userEmail = jwtService.getEmailFromToken(token);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User with e-mail " + userEmail + " was not found."));

        List<Cargo_handler> cargoHandlers = cargoHandlerRepository.findByUser(user);
        List<Cargo> cargos = cargoHandlers.stream().map( Cargo_handler::getCargo).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), cargos.size());
        List<CargoDto> cargoDtos = cargos.subList(start, end).stream()
                .map(cargoMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(cargoDtos, pageable, cargos.size());
    }

}
