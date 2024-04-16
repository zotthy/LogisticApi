package logistic.apilogistic.service;

import jakarta.persistence.EntityNotFoundException;
import logistic.apilogistic.Dtos.UserProfileDto;
import logistic.apilogistic.config.JwtService;
import logistic.apilogistic.dtoMapper.UserProfileDtoMapper;
import logistic.apilogistic.entity.Address;
import logistic.apilogistic.entity.User;
import logistic.apilogistic.repository.AddressRepository;
import logistic.apilogistic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {
    private final JwtService jwtService;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final UserProfileDtoMapper userProfileDtoMapper;

    @Autowired
    public ProfileService(JwtService jwtService, AddressRepository addressRepository, UserRepository userRepository,
                          UserProfileDtoMapper userProfileDtoMapper) {
        this.jwtService = jwtService;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.userProfileDtoMapper = userProfileDtoMapper;
    }

    @Transactional
    public void createAndAssignAddressToUser(String token, Address newAddress) {
        String userEmail = jwtService.getEmailFromToken(token);
        User user = userRepository.getUserByEmail(userEmail);

        if (user.getAddress() != null) {
            throw new IllegalArgumentException("This user already has an address registered.");
        }

        Address savedAddress = createAddress(newAddress);
        user.setAddress(savedAddress);
        userRepository.save(user);
    }

    private Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public UserProfileDto getProfile(String token){
        String userEmail = jwtService.getEmailFromToken(token);
        User user = userRepository.getUserByEmail(userEmail);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        return UserProfileDtoMapper.toDto(user);
    }
}
