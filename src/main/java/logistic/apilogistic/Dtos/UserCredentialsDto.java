package logistic.apilogistic.Dtos;

import java.util.Set;

public record UserCredentialsDto(String email, String password, Set<String> roles) {
}
