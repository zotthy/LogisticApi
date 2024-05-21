package logistic.apilogistic.service;

import jakarta.persistence.EntityNotFoundException;
import logistic.apilogistic.Dtos.DriverDto;
import logistic.apilogistic.repository.*;
import logistic.apilogistic.security.JwtService;
import logistic.apilogistic.dtoMapper.DriverDtoMapper;
import logistic.apilogistic.entity.*;
import logistic.apilogistic.exceptions.ExistsException;
import logistic.apilogistic.exceptions.ResourceNotFoundException;
import logistic.apilogistic.exceptions.UnauthorizedException;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MyDriverRepository myDriverRepository;
    private final CargoHandlerRepository cargoHandlerRepository;
    private final DriverDtoMapper driverDtoMapper;

    private final CargoRepository cargoRepository;
    public DriverService(DriverRepository driverRepository, JwtService jwtService, UserRepository userRepository,
                         MyDriverRepository myDriverRepository, CargoHandlerRepository cargoHandlerRepository,
                         DriverDtoMapper driverDtoMapper, CargoRepository cargoRepository) {
        this.driverRepository = driverRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.myDriverRepository = myDriverRepository;
        this.cargoHandlerRepository = cargoHandlerRepository;
        this.driverDtoMapper = driverDtoMapper;
        this.cargoRepository = cargoRepository;
    }

    @Transactional
    public Driver addDriver(DriverDto driverDto, String token) {
        String userEmail = jwtService.getEmailFromToken(token);
        Driver toSave = DriverDtoMapper.map(driverDto);
        Driver newDriver = driverRepository.save(toSave);

        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MyDrivers myDrivers = new MyDrivers();
        myDrivers.setDriver(newDriver);
        myDrivers.setUser(user);

        myDriverRepository.save(myDrivers);
        return newDriver;
    }
    public Page<DriverDto> getDriversForUser(String token, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String userEmail = jwtService.getEmailFromToken(token);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ExistsException("User not found"));
        Page<MyDrivers> myDrivers = myDriverRepository.findByUser(Optional.ofNullable(user), pageable);
        return myDrivers.map(myDriver -> {
            Driver driver = myDriver.getDriver();

            return DriverDtoMapper.map(driver);
        });
    }

    public Driver getDriverById(Long id, String token) {
        String userEmail = jwtService.getEmailFromToken(token);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ExistsException("User not found"));
        boolean isDriverOfUser = myDriverRepository.existsByUserIdAndDriverId(user.getId(), id);
        if (!isDriverOfUser) {
            throw new UnauthorizedException("User does not have access to this driver");
        }

        return driverRepository.findById(id)
                .orElseThrow(() -> new ExistsException("Driver with id " + id + " not found"));
    }
    public List<Cargo> getCargosForSpecificDriver(String token, Long driverId) {
        User user = userRepository.findByEmail(jwtService.getEmailFromToken(token)).orElseThrow(
                () -> new EntityNotFoundException("User not found."));
        Driver driver = driverRepository.findById(driverId).orElseThrow(
                () -> new EntityNotFoundException("Driver not found."));
        List<Cargo_handler> handlers = cargoHandlerRepository.findByDriver(driver);
        return handlers.stream().map(Cargo_handler::getCargo).collect(Collectors.toList());
    }

    public List<Cargo> getReaizationDrivers(String token, Long driverId) {
        String status = "Realization";
        User user = userRepository.findByEmail(jwtService.getEmailFromToken(token)).orElseThrow(
                () -> new EntityNotFoundException("User not found."));
        Driver driver = driverRepository.findById(driverId).orElseThrow(
                () -> new EntityNotFoundException("Driver not found."));
        List<Cargo_handler> handlers = cargoHandlerRepository.findByDriver(driver);
        return handlers.stream()
                .map(Cargo_handler::getCargo)
                .filter(cargo -> cargo.getStatus().equals(status))
                .collect(Collectors.toList());
    }
    public List<Cargo> getCompleteDrivers(String token, Long driverId) {
        String status = "Complete";
        User user = userRepository.findByEmail(jwtService.getEmailFromToken(token)).orElseThrow(
                () -> new EntityNotFoundException("User not found."));
        Driver driver = driverRepository.findById(driverId).orElseThrow(
                () -> new EntityNotFoundException("Driver not found."));
        List<Cargo_handler> handlers = cargoHandlerRepository.findByDriver(driver);
        return handlers.stream()
                .map(Cargo_handler::getCargo)
                .filter(cargo -> cargo.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    public Page<DriverDto> getAvalibleDrivers(String token, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String userEmail = jwtService.getEmailFromToken(token);

        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ExistsException("User not found"));
        Page<MyDrivers> myDrivers = myDriverRepository.findByUser(Optional.ofNullable(user), pageable);

        List<DriverDto> driverDtos = myDrivers.stream()
                .filter(myDriver -> {
                    Driver driver = myDriver.getDriver();
                    List<Cargo_handler> activeCargos = cargoHandlerRepository.findActiveCargosByDriver(driver, "Realization");
                    return activeCargos.isEmpty();
                })
                .map(myDriver -> {
                    Driver driver = myDriver.getDriver();
                    return DriverDtoMapper.map(driver);
                })
                .collect(Collectors.toList());
        return new PageImpl<>(driverDtos, pageable, driverDtos.size());
    }
    public Cargo completeCargo(Long cargoId) {
        String status = "Complete";
        Cargo cargo = cargoRepository.findById(cargoId)
                .orElseThrow(() -> new IllegalStateException("Cargo not found"));

        cargo.setStatus(status);

        return cargoRepository.saveAndFlush(cargo);
    }
}
