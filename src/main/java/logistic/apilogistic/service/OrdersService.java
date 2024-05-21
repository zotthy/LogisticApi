package logistic.apilogistic.service;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import logistic.apilogistic.Dtos.CargoDto;
import logistic.apilogistic.security.JwtService;
import logistic.apilogistic.dtoMapper.CargoMapper;
import logistic.apilogistic.entity.Cargo;
import logistic.apilogistic.entity.Cargo_handler;
import logistic.apilogistic.entity.Driver;
import logistic.apilogistic.entity.User;
import logistic.apilogistic.repository.CargoHandlerRepository;
import logistic.apilogistic.repository.CargoRepository;
import logistic.apilogistic.repository.DriverRepository;
import logistic.apilogistic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrdersService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CargoRepository cargoRepository;
    private final CargoHandlerRepository cargoHandlerRepository;
    private final CargoMapper cargoMapper;
    private final DriverRepository driverRepository;
    private final MailService mailService;

    @Autowired
    public OrdersService(JwtService jwtService, UserRepository userRepository,
                         CargoRepository cargoRepository, CargoHandlerRepository cargoHandlerRepository,
                         CargoMapper cargoMapper, DriverRepository driverRepository, MailService mailService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.cargoRepository = cargoRepository;
        this.cargoHandlerRepository = cargoHandlerRepository;
        this.cargoMapper = cargoMapper;
        this.driverRepository = driverRepository;
        this.mailService = mailService;
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
    @Transactional
    public void assignCargoHandler(String token, Long cargoId,Long driverId) throws MessagingException {
        User user = userRepository.findByEmail(jwtService.getEmailFromToken(token))
                .orElseThrow(() -> new EntityNotFoundException("User with email was not found."));

        Cargo cargo = cargoRepository.findById(cargoId)
                .orElseThrow(() -> new EntityNotFoundException("Cargo with id was not found."));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver with id was not found."));

        //System.out.println(driver.getEmail());
        //System.out.println(cargoMapper.toDto(cargo));
        String subject = "Masz nowe zlecenie!";
        String text = String.format("Cześć" +
                        "Otrzymałeś nowe zlecenie transportowe! " +
                        "Poniżej znajdują się szczegóły zlecenia: " +
                        cargo.toString(),
                        "Prosimy o jak najszybsze udanie się do miejsca załadunku i realizację zlecenia zgodnie z powyższymi informacjami. " +
                        "Jeśli masz jakiekolwiek pytania, skontaktuj się z nami bezpośrednio." +
                        "Dziękujemy za Twoją pracę i zaangażowanie!");

        mailService.sendMail(driver.getEmail(), subject, text,true);
        Cargo_handler cargoHandler = new Cargo_handler();
        cargoHandler.setUser(user);
        cargoHandler.setCargo(cargo);
        cargoHandler.setDriver(driver);

        cargoHandlerRepository.save(cargoHandler);
    }
    public Map<Driver, List<Cargo>> getDriversAndCargosForUser(String token) {
        User user = userRepository.findByEmail(jwtService.getEmailFromToken(token)).orElseThrow(
                () -> new EntityNotFoundException("User not found."));
        List<Cargo_handler> handlers = cargoHandlerRepository.findByUser(user);

        Map<Driver, List<Cargo>> result = new HashMap<>();

        for (Cargo_handler handler : handlers) {
            result.computeIfAbsent(handler.getDriver(), k -> new ArrayList<>()).add(handler.getCargo());
        }

        return result;
    }
}
