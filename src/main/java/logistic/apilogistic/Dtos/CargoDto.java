package logistic.apilogistic.Dtos;

import java.io.Serializable;

/**
 * DTO for {@link logistic.apilogistic.entity.Cargo}
 */
public class CargoDto implements Serializable {
    private Long id;
    private Double price;
    private String typeCargo;
    private String status;
    private String owner;
    private CargoAddressDto loadAddress;
    private CargoAddressDto unloadAddress;

    public CargoDto(Long id, Double price, String typeCargo, String status, String owner, CargoAddressDto loadAddress, CargoAddressDto unloadAddress) {
        this.id = id;
        this.price = price;
        this.typeCargo = typeCargo;
        this.status = status;
        this.owner = owner;
        this.loadAddress = loadAddress;
        this.unloadAddress = unloadAddress;
    }

    public CargoDto() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTypeCargo(String typeCargo) {
        this.typeCargo = typeCargo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setLoadAddress(CargoAddressDto loadAddress) {
        this.loadAddress = loadAddress;
    }

    public void setUnloadAddress(CargoAddressDto unloadAddress) {
        this.unloadAddress = unloadAddress;
    }

    public String getOwner() {
        return owner;
    }

    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public String getTypeCargo() {
        return typeCargo;
    }

    public String getStatus() {
        return status;
    }

    public CargoAddressDto getLoadAddress() {
        return loadAddress;
    }

    public CargoAddressDto getUnloadAddress() {
        return unloadAddress;
    }

    @Override
    public String toString() {
        return "CargoDto{" +
                "id=" + id +
                ", price=" + price +
                ", typeCargo='" + typeCargo + '\'' +
                ", status='" + status + '\'' +
                ", loadAddress=" + loadAddress +
                ", unloadAddress=" + unloadAddress +
                '}';
    }
}