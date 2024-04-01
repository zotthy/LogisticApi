package logistic.apilogistic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myjob")
public class MyJobController {
    private final CargoService cargoService;

    @Autowired
    public MyJobController(CargoService cargoService) {
        this.cargoService = cargoService;
    }
}
