package logistic.apilogistic.dtoMapper;

import logistic.apilogistic.Dtos.CargoAddressDto;
import logistic.apilogistic.entity.CargoAddress;
import org.springframework.stereotype.Service;

@Service
public class CargoAddressMapper {

    public static CargoAddress toEntity(CargoAddressDto cargoAddressDto) {
        if (cargoAddressDto == null) {
            return null;
        }

        return new CargoAddress(
                cargoAddressDto.getId(),
                cargoAddressDto.getStreet(),
                cargoAddressDto.getCity(),
                cargoAddressDto.getZip_code()
        );
    }
    public static CargoAddress maptoEntity(CargoAddressDto cargoAddressDto){
        CargoAddress cargoAddress = new CargoAddress();
        cargoAddress.setCity(cargoAddressDto.getCity());
        cargoAddress.setStreet(cargoAddressDto.getStreet());
        cargoAddress.setZip_code(cargoAddressDto.getZip_code());
        return cargoAddress;
    }
    public static CargoAddressDto mapToDto(CargoAddress cargoAddress){
        CargoAddressDto cargoAddressDto = new CargoAddressDto();
        if (cargoAddress != null) {
            cargoAddressDto.setId(cargoAddress.getId());
            cargoAddressDto.setCity(cargoAddress.getCity());
            cargoAddressDto.setStreet(cargoAddress.getStreet());
            cargoAddressDto.setZip_code(cargoAddress.getZip_code());
        }
        return cargoAddressDto;
    }
}