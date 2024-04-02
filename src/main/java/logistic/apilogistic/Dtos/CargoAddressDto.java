package logistic.apilogistic.Dtos;

import logistic.apilogistic.entity.CargoAddress;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link CargoAddress}
 */
public class CargoAddressDto implements Serializable {
    private Long id;
    private String street;
    private String city;
    private String province;
    private String zip_code;

    public CargoAddressDto(Long id, String street, String city, String zip_code) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.zip_code = zip_code;
    }

    public CargoAddressDto() {

    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZip_code() {
        return zip_code;
    }

    @Override
    public String toString() {
        return "CargoAddressDto{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zip_code='" + zip_code + '\'' +
                '}';
    }
}