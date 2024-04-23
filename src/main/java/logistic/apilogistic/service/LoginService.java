package logistic.apilogistic.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import logistic.apilogistic.authRequest.LoginRequest;
import logistic.apilogistic.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private ObjectMapper objectMapper;

    public LoginService(AuthenticationManager authenticationManager, JwtService jwtService, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public String authenticateAndCreateToken(LoginRequest loginRequest) throws JsonProcessingException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String tokenResponse = jwtService.createSignedJWT(authentication.getName(),authorities);
        //TokenDTO tokenDTO = new TokenDTO(tokenResponse);
        //String tokenJson = objectMapper.writeValueAsString(tokenDTO);
        //return tokenJson;
        return tokenResponse;
    }
}
