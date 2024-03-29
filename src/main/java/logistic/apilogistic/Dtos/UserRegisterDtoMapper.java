package logistic.apilogistic.Dtos;

import logistic.apilogistic.authRequest.RegisterRequest;
import logistic.apilogistic.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterDtoMapper {

    public static User mapDtoToEntity(RegisterRequest registerRequest){
        User user = new User();
        user.setId(registerRequest.getId());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        return user;
    }

}
