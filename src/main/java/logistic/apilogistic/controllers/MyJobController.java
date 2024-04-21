package logistic.apilogistic.controllers;

import logistic.apilogistic.Dtos.CargoDto;
import logistic.apilogistic.entity.Cargo_handler;
import logistic.apilogistic.service.CargoService;
import logistic.apilogistic.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class MyJobController {
    private final OrdersService ordersService;
    private final CargoService cargoService;

    @Autowired
    public MyJobController(OrdersService ordersService, CargoService cargoService) {
        this.ordersService = ordersService;
        this.cargoService = cargoService;
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
    @GetMapping("/user")
    public List<Cargo_handler> getCargoHandlersForUser(@RequestHeader (name="Authorization") String token) {
        return ordersService.getCargoHandlersForUser(token);
    }
}
