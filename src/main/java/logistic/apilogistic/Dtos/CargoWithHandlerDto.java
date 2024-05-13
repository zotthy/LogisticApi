package logistic.apilogistic.Dtos;


import logistic.apilogistic.entity.Cargo;
import logistic.apilogistic.entity.CargoAddress;
import logistic.apilogistic.entity.Driver;
import logistic.apilogistic.entity.User;

import java.time.LocalDateTime;

public class CargoWithHandlerDto {

    // Include the properties you want from the Cargo class
    private Long cargoId;
    private Double cargoPrice;
    private String typeCargo;
    private String cargoStatus;
    private String cargoOwner;
    private CargoAddress unloadAddress;
    private CargoAddress loadAddress;
    private LocalDateTime cargoDateTime;

    // Include the properties you want from the User and Driver classes
    private Long handlerId;
    private String handlerFirstName;
    private String handlerLastName;

    private Long driverId;
    private String driverFirstName;
    private String driverSurname;


    // Constructor, getters, setters
    public CargoWithHandlerDto(Cargo cargo, User handler, Driver driver,
                               CargoAddress unloadAddress, CargoAddress loadAddress) {
        this.cargoId = cargo.getId();
        this.cargoPrice = cargo.getPrice();
        this.typeCargo = cargo.getTypeCargo();
        this.cargoStatus = cargo.getStatus();
        this.cargoOwner = cargo.getOwner();
        this.cargoDateTime = cargo.getDateTime();
        this.unloadAddress = unloadAddress;
        this.loadAddress = loadAddress;

        if (handler != null) {
            this.handlerId = handler.getId();
            this.handlerFirstName = handler.getFirstName();
            this.handlerLastName = handler.getLastName();
        }

        if (driver != null) {
            this.driverId = driver.getId();
            this.driverFirstName = driver.getName();
            this.driverSurname = driver.getSurname();
        }
    }

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }

    public Double getCargoPrice() {
        return cargoPrice;
    }

    public void setCargoPrice(Double cargoPrice) {
        this.cargoPrice = cargoPrice;
    }

    public String getTypeCargo() {
        return typeCargo;
    }

    public void setTypeCargo(String typeCargo) {
        this.typeCargo = typeCargo;
    }

    public String getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(String cargoStatus) {
        this.cargoStatus = cargoStatus;
    }

    public String getCargoOwner() {
        return cargoOwner;
    }

    public void setCargoOwner(String cargoOwner) {
        this.cargoOwner = cargoOwner;
    }

    public LocalDateTime getCargoDateTime() {
        return cargoDateTime;
    }

    public void setCargoDateTime(LocalDateTime cargoDateTime) {
        this.cargoDateTime = cargoDateTime;
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public String getHandlerFirstName() {
        return handlerFirstName;
    }

    public void setHandlerFirstName(String handlerFirstName) {
        this.handlerFirstName = handlerFirstName;
    }

    public String getHandlerLastName() {
        return handlerLastName;
    }

    public CargoAddress getUnloadAddress() {
        return unloadAddress;
    }

    public void setUnloadAddress(CargoAddress unloadAddress) {
        this.unloadAddress = unloadAddress;
    }

    public CargoAddress getLoadAddress() {
        return loadAddress;
    }

    public void setLoadAddress(CargoAddress loadAddress) {
        this.loadAddress = loadAddress;
    }

    public void setHandlerLastName(String handlerLastName) {
        this.handlerLastName = handlerLastName;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverFirstName() {
        return driverFirstName;
    }

    public void setDriverFirstName(String driverFirstName) {
        this.driverFirstName = driverFirstName;
    }

    public String getDriverSurname() {
        return driverSurname;
    }

    public void setDriverSurname(String driverSurname) {
        this.driverSurname = driverSurname;
    }
}