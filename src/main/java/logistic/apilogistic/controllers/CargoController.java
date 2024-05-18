package logistic.apilogistic.controllers;

import logistic.apilogistic.Dtos.CargoDto;
import logistic.apilogistic.Dtos.CargoWithHandlerDto;
import logistic.apilogistic.service.CargoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
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
    ResponseEntity<?> cargoAdd(@RequestHeader("Authorization") String token,
                               @RequestBody CargoDto cargoDto){
        CargoDto saved = cargoService.add(token,cargoDto);
        URI savedCompanyUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(savedCompanyUri).body(saved);
    }

    @GetMapping("/my/cargo")
    public Page<CargoWithHandlerDto> getMyCargos(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestHeader("Authorization") String token) {
        return cargoService.getCargosByUser(token,page,size);
    }
    @GetMapping("/my/actual/cargo")
    public Page<CargoWithHandlerDto> getMyCargosActual(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestHeader("Authorization") String token) {
        String status = "Realization";
        return cargoService.getCargosByUserAndStatus(token,page,size,status);
    }
    @GetMapping("/my/history/cargo")
    public Page<CargoWithHandlerDto> getMyCargosHistory(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestHeader("Authorization") String token) {
        String status = "Complete";
        return cargoService.getCargosByUserAndStatus(token,page,size,status);
    }
    @GetMapping("/cargos")
    public Page<CargoWithHandlerDto> getcargo(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestHeader("Authorization") String token) {
        return cargoService.getAllCargosWithHandlerNull(page,size);
    }
    @GetMapping("/my/cargo/{id}")
    public CargoWithHandlerDto getMyCargo(@PathVariable Long id, @RequestHeader("Authorization") String token){
        return cargoService.getCargoByUserAndId(token, id);
    }
}
