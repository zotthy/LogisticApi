package logistic.apilogistic.controllers;

import logistic.apilogistic.Dtos.CargoDto;
import logistic.apilogistic.entity.Cargo;
import logistic.apilogistic.entity.Cargo_handler;
import logistic.apilogistic.entity.Driver;
import logistic.apilogistic.service.CargoService;
import logistic.apilogistic.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/orders")
@CrossOrigin
public class MyJobController {
    private final OrdersService ordersService;

    @Autowired
    public MyJobController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }
    @GetMapping("/cargo")
    public Page<CargoDto> getMyCargos(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestHeader("Authorization") String token) {
        return ordersService.getCargosByUser(token,page,size);
    }
    @PostMapping("/cargo/{cargoId}/take/{driverId}")
    public ResponseEntity<?> addHandlerToCargo(@RequestHeader("Authorization") String token,
                                               @PathVariable Long cargoId,
                                               @PathVariable Long driverId){
        ordersService.assignCargoHandler(token, cargoId,driverId);
        return ResponseEntity.ok("Cargo handler added successfully");
    }
    //////////////////
    @GetMapping("/user/driversAndCargos")
    public Map<Driver, List<Cargo>> getDriversAndCargosForUser(@RequestHeader (name="Authorization") String token) {
        return ordersService.getDriversAndCargosForUser(token);
    }
}
