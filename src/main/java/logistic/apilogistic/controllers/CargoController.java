package logistic.apilogistic.controllers;

import logistic.apilogistic.entity.Cargo;
import logistic.apilogistic.entity.CargoAddress;
import logistic.apilogistic.entity.User;
import logistic.apilogistic.service.CargoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CargoController {
    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }
    @PostMapping("/add")
    ResponseEntity<?> addcargo(@RequestHeader("Authorization") String token){
        cargoService.addCargo(token);
        return new ResponseEntity<>("add", HttpStatus.OK);
    }
}
