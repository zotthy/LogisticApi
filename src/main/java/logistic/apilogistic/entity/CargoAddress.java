package logistic.apilogistic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
public class CargoAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(max = 100)
    @NotNull
    private String street;
    @Length(max = 100)
    @NotNull
    private String city;
    @Length(max = 100)
    @NotNull
    private String province;
    @Length(max = 6)
    @NotNull
    private String zip_code;

    public CargoAddress(Long id, String street, String city, String zipCode, String zip_code) {
    }

    public CargoAddress() {

    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    @Override
    public String toString() {
        return "CargoAddress{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zip_code='" + zip_code + '\'' +
                '}';
    }
}
