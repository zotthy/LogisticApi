package logistic.apilogistic.service;

import logistic.apilogistic.config.JwtService;
import logistic.apilogistic.entity.Cargo;
import logistic.apilogistic.entity.CargoAddress;
import logistic.apilogistic.repository.CargoAddressRepository;
import logistic.apilogistic.repository.CargoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class CargoService {
    private final CargoAddressRepository cargoAddressRepository;
    private final CargoRepository cargoRepository;
    private final UserCredentialsService userCredentialsService;
    private final JwtService jwtService;


    public CargoService(CargoAddressRepository cargoAddressRepository, CargoRepository cargoRepository,
                        UserCredentialsService userCredentialsService, JwtService jwtService) {
        this.cargoAddressRepository = cargoAddressRepository;
        this.cargoRepository = cargoRepository;
        this.userCredentialsService = userCredentialsService;
        this.jwtService = jwtService;
    }

    public void addCargo(String token) {

        String user_email = getEmailFromToken(token);
        System.out.println(user_email);
    }

    private String getEmailFromToken(String token) {
        return jwtService.getEmailFromToken(token);
    }
}
