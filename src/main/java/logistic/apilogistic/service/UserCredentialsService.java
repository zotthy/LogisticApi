package logistic.apilogistic.service;

import logistic.apilogistic.Dtos.UserCredentialsDto;
import logistic.apilogistic.Dtos.UserCredentialsDtoMapper;
import logistic.apilogistic.Dtos.UserRegisterDtoMapper;
import logistic.apilogistic.authRequest.RegisterRequest;
import logistic.apilogistic.entity.User;
import logistic.apilogistic.entity.UserRole;
import logistic.apilogistic.exceptions.ExistsException;
import logistic.apilogistic.repository.UserRepository;
import logistic.apilogistic.repository.UserRoleRepozitory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCredentialsService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepozitory userRoleRepozitory;
    private final UserRegisterDtoMapper userRegisterDtoMapper;

    public UserCredentialsService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleRepozitory userRoleRepozitory, UserRegisterDtoMapper userRegisterDtoMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepozitory = userRoleRepozitory;
        this.userRegisterDtoMapper = userRegisterDtoMapper;
    }

    public Optional<UserCredentialsDto> findCredentialsByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserCredentialsDtoMapper::map);
    }

    public void register(RegisterRequest registerRequest){
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new ExistsException("User is exists");
        }

        User user = UserRegisterDtoMapper.mapDtoToEntity(registerRequest);
        String password = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(password);
        UserRole userRole = userRoleRepozitory.findByName("USER")
                .orElseThrow(()->new RuntimeException("Not found"));
        user.getRoles().add(userRole);
        userRepository.save(user);
    }
}
