package logistic.apilogistic.service;

import logistic.apilogistic.Dtos.DriverDto;
import logistic.apilogistic.config.JwtService;
import logistic.apilogistic.dtoMapper.DriverDtoMapper;
import logistic.apilogistic.entity.Driver;
import logistic.apilogistic.entity.MyDrivers;
import logistic.apilogistic.entity.User;
import logistic.apilogistic.exceptions.ExistsException;
import logistic.apilogistic.exceptions.ResourceNotFoundException;
import logistic.apilogistic.exceptions.UnauthorizedException;
import logistic.apilogistic.repository.CargoHandlerRepository;
import logistic.apilogistic.repository.DriverRepository;
import logistic.apilogistic.repository.MyDriverRepository;
import logistic.apilogistic.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MyDriverRepository myDriverRepository;
    private final CargoHandlerRepository cargoHandlerRepository;
    private final DriverDtoMapper driverDtoMapper;

    public DriverService(DriverRepository driverRepository, JwtService jwtService, UserRepository userRepository,
                         MyDriverRepository myDriverRepository, CargoHandlerRepository cargoHandlerRepository, DriverDtoMapper driverDtoMapper) {
        this.driverRepository = driverRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.myDriverRepository = myDriverRepository;
        this.cargoHandlerRepository = cargoHandlerRepository;
        this.driverDtoMapper = driverDtoMapper;
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
}
