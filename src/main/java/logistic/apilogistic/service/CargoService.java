package logistic.apilogistic.service;

import jakarta.persistence.EntityNotFoundException;
import logistic.apilogistic.Dtos.CargoAddressDto;
import logistic.apilogistic.Dtos.CargoDto;
import logistic.apilogistic.security.JwtService;
import logistic.apilogistic.dtoMapper.CargoAddressMapper;
import logistic.apilogistic.dtoMapper.CargoMapper;
import logistic.apilogistic.entity.*;
import logistic.apilogistic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final CargoHandlerRepository cargoHandlerRepository;
    private final DriverRepository driverRepository;

    @Autowired
    public CargoService(CargoAddressRepository cargoAddressRepository, CargoRepository cargoRepository,
                        CargoMapper cargoMapper, CargoAddressMapper cargoAddressMapper, JwtService jwtService,
                        UserRepository userRepository, CargoOwnersRepository cargoOwnersRepository,
                        CargoHandlerRepository cargoHandlerRepository, DriverRepository driverRepository) {
        this.cargoAddressRepository = cargoAddressRepository;
        this.cargoRepository = cargoRepository;
        this.cargoMapper = cargoMapper;
        this.jwtService = jwtService;
        this.cargoAddressMapper = cargoAddressMapper;
        this.userRepository = userRepository;
        this.cargoOwnersRepository = cargoOwnersRepository;
        this.cargoHandlerRepository = cargoHandlerRepository;
        this.driverRepository = driverRepository;
    }

    public Optional<CargoDto> getCargoById(Long id) {
        Optional<Cargo> cargo = cargoRepository.findById(id);
        return cargo.map(cargoMapper::toDto);
    }

    public Page<CargoDto> page(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Cargo> cargoPage = cargoRepository.findAll(pageable);
        List<CargoDto> cargoDtoList = cargoPage.getContent().stream()
                .map(cargoMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(cargoDtoList, pageable, cargoPage.getTotalElements());
    }

    public Page<CargoDto> getCargosByUser(String token, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String userEmail = jwtService.getEmailFromToken(token);

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User with e-mail " + userEmail + " was not found."));

        List<Cargo_owners> cargoOwnersList = cargoOwnersRepository.findByUser(user);
        List<Cargo> cargos = cargoOwnersList.stream().map(Cargo_owners::getCargo).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), cargos.size());
        List<CargoDto> cargoDtos = cargos.subList(start, end).stream()
                .map(cargoMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(cargoDtos, pageable, cargos.size());
    }
    @Transactional
    public CargoDto add(String token, CargoDto cargoDto) {

        String email = jwtService.getEmailFromToken(token);
        System.out.println(email);

        CargoAddressDto cargoAddressLoadDto = cargoDto.getLoadAddress();
        CargoAddress loadAdress = CargoAddressMapper.maptoEntity(cargoAddressLoadDto);
        CargoAddressDto cargoAddressUnloadDto = cargoDto.getUnloadAddress();
        CargoAddress unloadadress = CargoAddressMapper.maptoEntity(cargoAddressUnloadDto);

        cargoAddressRepository.save(loadAdress);
        cargoAddressRepository.save(unloadadress);

        cargoDto.setLocalDateTime(LocalDateTime.now());
        Cargo cargo = CargoMapper.toEntity(cargoDto);
        cargo.setLoadAddress(loadAdress);
        cargo.setUnloadAddress(unloadadress);
        cargo.setOwner(email);

        cargoRepository.save(cargo);

        Cargo_owners owners = new Cargo_owners();
        owners.setCargo(cargo);
        owners.setUser(userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with ID not found.")));
        cargoOwnersRepository.save(owners);

        return cargoDto;
    }
    public List<CargoDto> getCargosByLoadAddressProvince(String province) {
        return cargoRepository.findByLoadAddressProvince(province);
    }

}