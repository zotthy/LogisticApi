package logistic.apilogistic.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "cargo")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Double price;
    private String typeCargo;
    private String status;

    private String owner;

    @ManyToOne
    @JoinColumn(name = "load_address_id", nullable = false)
    private CargoAddress loadAddress;

    @ManyToOne
    @JoinColumn(name = "unload_address_id", nullable = false)
    private CargoAddress unloadAddress;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTypeCargo() {
        return typeCargo;
    }

    public void setTypeCargo(String typeCargo) {
        this.typeCargo = typeCargo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CargoAddress getLoadAddress() {
        return loadAddress;
    }

    public void setLoadAddress(CargoAddress loadAddress) {
        this.loadAddress = loadAddress;
    }

    public CargoAddress getUnloadAddress() {
        return unloadAddress;
    }

    public void setUnloadAddress(CargoAddress unloadAddress) {
        this.unloadAddress = unloadAddress;
    }
}
