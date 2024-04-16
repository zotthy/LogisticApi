package logistic.apilogistic.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDTO {
    @JsonProperty("token")
    private String token;

    public TokenDTO() {
    }

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}