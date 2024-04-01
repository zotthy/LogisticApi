package logistic.apilogistic.service;

import jakarta.persistence.EntityNotFoundException;
import logistic.apilogistic.Dtos.CargoAddressDto;
import logistic.apilogistic.Dtos.CargoDto;
import logistic.apilogistic.config.JwtService;
import logistic.apilogistic.dtoMapper.CargoAddressMapper;
import logistic.apilogistic.dtoMapper.CargoMapper;
import logistic.apilogistic.entity.Cargo;
import logistic.apilogistic.entity.CargoAddress;
import logistic.apilogistic.entity.Cargo_owners;
import logistic.apilogistic.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CargoService {

    private final CargoAddressRepository cargoAddressRepository;
    private final CargoRepository cargoRepository;
    private final CargoAddressMapper cargoAddressMapper;
    private final CargoMapper cargoMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CargoOwnersRepository cargoOwnersRepository;

    public CargoService(CargoAddressRepository cargoAddressRepository, CargoRepository cargoRepository,
                        CargoMapper cargoMapper, CargoAddressMapper cargoAddressMapper, JwtService jwtService,
                        UserRepository userRepository, CargoOwnersRepository cargoOwnersRepository) {
        this.cargoAddressRepository = cargoAddressRepository;
        this.cargoRepository = cargoRepository;
        this.cargoMapper = cargoMapper;
        this.jwtService = jwtService;
        this.cargoAddressMapper = cargoAddressMapper;
        this.userRepository = userRepository;
        this.cargoOwnersRepository = cargoOwnersRepository;
    }

    public Optional<CargoDto> getCargoById(Long id){
        Optional<Cargo> cargo = cargoRepository.findById(id);
        return cargo.map(cargoMapper::toDto);
    }
    public Page<CargoDto> page(int page,int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Cargo> cargoPage = cargoRepository.findAll(pageable);
        List<CargoDto> cargoDtoList = cargoPage.getContent().stream()
                .map(cargoMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(cargoDtoList, pageable, cargoPage.getTotalElements());
    }

    public CargoDto add(String token,CargoDto cargoDto) {

        String email = jwtService.getEmailFromToken(token);
        System.out.println(email);

        CargoAddressDto cargoAddressLoadDto = cargoDto.getLoadAddress();
        CargoAddress loadAdress = CargoAddressMapper.maptoEntity(cargoAddressLoadDto);
        CargoAddressDto cargoAddressUnloadDto = cargoDto.getUnloadAddress();
        CargoAddress unloadadress = CargoAddressMapper.maptoEntity(cargoAddressUnloadDto);

        cargoAddressRepository.save(loadAdress);
        cargoAddressRepository.save(unloadadress);

        Cargo cargo = CargoMapper.toEntity(cargoDto);
        cargo.setLoadAddress(loadAdress);
        cargo.setUnloadAddress(unloadadress);
        cargo.setOwner(email);
        cargoRepository.save(cargo);

        Cargo_owners owners = new Cargo_owners();
        owners.setCargo(cargo);
        owners.setUser(userRepository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("User with ID not found.")));

        cargoOwnersRepository.save(owners);

        return cargoDto;
    }

}