package logistic.apilogistic.dtoMapper;

import logistic.apilogistic.Dtos.AddressDto;
import logistic.apilogistic.Dtos.UserProfileDto;
import logistic.apilogistic.entity.Address;
import logistic.apilogistic.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserProfileDtoMapper {
    public static UserProfileDto toDto(User user){
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setFirstName(user.getFirstName());
        userProfileDto.setLastName(user.getLastName());
        userProfileDto.setEmail(user.getEmail());

        Address address = user.getAddress();

        if (address != null){
            AddressDto addressDto = new AddressDto();
            addressDto.setId(address.getId());
            addressDto.setStreet(address.getStreet());
            addressDto.setCity(address.getCity());
            addressDto.setZip_code(address.getZip_code());

            userProfileDto.setAddress(addressDto);
        }
        return userProfileDto;
    }
}
