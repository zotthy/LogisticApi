package logistic.apilogistic.controllers;

import logistic.apilogistic.Dtos.DriverDto;
import logistic.apilogistic.entity.Cargo;
import logistic.apilogistic.entity.Driver;
import logistic.apilogistic.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
public class DriversController {
    private final DriverService driverService;

    @Autowired
    public DriversController(DriverService driverService) {
        this.driverService = driverService;
    }
    @PostMapping("/driverNew")
    public ResponseEntity<?> addDriver(@RequestBody DriverDto driverDto,
                                       @RequestHeader("Authorization") String token) {
        Driver savedDriver = driverService.addDriver(driverDto,token);
        URI savec = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(savedDriver.getId())
                .toUri();
        return ResponseEntity.created(savec).body(savedDriver);
    }
    @GetMapping("/drivers")
    public Page<DriverDto> getAllDrivers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestHeader("Authorization") String token){
        return driverService.getDriversForUser(token,page,size);
    }
    @GetMapping("/driver/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id,
                                                @RequestHeader("Authorization") String token) {
        Driver driver = driverService.getDriverById(id,token);
        return ResponseEntity.ok(driver);
    }
    @GetMapping("/driver/{driverId}/cargos")
    public List<Cargo> getCargosForDriver(
            @RequestHeader (name="Authorization") String token,
            @PathVariable Long driverId) {
        return driverService.getCargosForSpecificDriver(token, driverId);
    }
}
