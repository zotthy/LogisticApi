package logistic.apilogistic.Dtos;

import logistic.apilogistic.entity.Cargo;
import logistic.apilogistic.entity.Driver;

import java.util.List;

public class CargoHandlerDto {
    private Driver driver;
    private List<Cargo> cargos;

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }
}
