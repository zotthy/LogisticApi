package logistic.apilogistic.controllers;

import logistic.apilogistic.Dtos.CargoDto;
import logistic.apilogistic.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
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
}
