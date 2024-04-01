package logistic.apilogistic.dtoMapper;

import logistic.apilogistic.Dtos.CargoAddressDto;
import logistic.apilogistic.Dtos.CargoDto;
import logistic.apilogistic.entity.Cargo;
import logistic.apilogistic.entity.CargoAddress;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CargoMapper {
    public static Cargo toEntity(CargoDto cargoDto) {
        Cargo cargo = new Cargo();
        cargo.setPrice(cargoDto.getPrice());
        cargo.setTypeCargo(cargoDto.getTypeCargo());
        cargo.setStatus(cargoDto.getStatus());
        cargo.setOwner(cargo.getOwner());
        CargoAddress loadAddress = CargoAddressMapper.toEntity(cargoDto.getLoadAddress());
        CargoAddress unloadAddress = CargoAddressMapper.toEntity(cargoDto.getUnloadAddress());
        cargo.setLoadAddress(loadAddress);
        cargo.setUnloadAddress(unloadAddress);
        return cargo;
    }

    public CargoDto toDto(Cargo cargo) {
        System.out.println(cargo);
        CargoDto cargoDto = new CargoDto();
        cargoDto.setId(cargo.getId());
        cargoDto.setPrice(cargo.getPrice());
        cargoDto.setTypeCargo(cargo.getTypeCargo());
        cargoDto.setStatus(cargo.getStatus());
        cargoDto.setOwner(cargo.getOwner());
        CargoAddressDto loadAddressDto = CargoAddressMapper.mapToDto(cargo.getLoadAddress());
        CargoAddressDto unloadAddressDto = CargoAddressMapper.mapToDto(cargo.getUnloadAddress());
        cargoDto.setLoadAddress(loadAddressDto);
        cargoDto.setUnloadAddress(unloadAddressDto);
        return cargoDto;
    }
}