package logistic.apilogistic.controllers;

import logistic.apilogistic.Dtos.CargoAddressDto;
import logistic.apilogistic.Dtos.CargoDto;
import logistic.apilogistic.entity.Cargo;
import logistic.apilogistic.service.CargoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CargoController {
    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }
    @GetMapping("/cargo")
    public Page<CargoDto> getCargos(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return cargoService.page(page, size);
    }
    @GetMapping("/cargo/{id}")
    public ResponseEntity<CargoDto> getCargoById(@PathVariable Long id) {
        Optional<CargoDto> cargoDto = cargoService.getCargoById(id);
        return cargoDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/cargo/add")
    ResponseEntity<?> cargoAdd(@RequestHeader("Authorization") String token, @RequestBody CargoDto cargoDto){
        CargoDto saved = cargoService.add(token,cargoDto);
        URI savedCompanyUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(savedCompanyUri).body(saved);
    }
}
