package logistic.apilogistic.user.dto;

import java.util.Set;

public record UserCredentialsDto(String email, String password, Set<String> roles) {
}
