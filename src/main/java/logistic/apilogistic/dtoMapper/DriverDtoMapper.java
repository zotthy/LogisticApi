package logistic.apilogistic.dtoMapper;

import logistic.apilogistic.Dtos.DriverDto;
import logistic.apilogistic.entity.Driver;
import org.springframework.stereotype.Component;

@Component
public class DriverDtoMapper {
    public static Driver map(DriverDto driverDto){
        Driver driver = new Driver();
        driver.setName(driverDto.getName());
        driver.setSurname(driverDto.getSurname());
        driver.setNumber(driverDto.getNumber());

        return driver;
    }
    public static DriverDto map(Driver driver){
        DriverDto driverDto = new DriverDto();
        driverDto.setId(driver.getId());
        driverDto.setName(driver.getName());
        driverDto.setSurname(driver.getSurname());
        driverDto.setNumber(driver.getNumber());
        return driverDto;
    }
}
