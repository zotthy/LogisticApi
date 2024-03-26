package logistic.apilogistic.user;

import logistic.apilogistic.user.dto.UserCredentialsDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCredentialsService {

    private final UserRepository userRepository;

    public UserCredentialsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserCredentialsDto> findCredentialsByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserCredentialsDtoMapper::map);
    }
}
