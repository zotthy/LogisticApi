package logistic.apilogistic.controllers;

import logistic.apilogistic.authRequest.LoginRequest;
import logistic.apilogistic.authRequest.RegisterRequest;
import logistic.apilogistic.config.JwtService;
import logistic.apilogistic.exceptions.ExistsException;
import logistic.apilogistic.service.UserCredentialsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserCredentialsService userCredentialsService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserCredentialsService userCredentialsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userCredentialsService = userCredentialsService;
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.createSignedJWT(authentication.getName(),authorities);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest, BindingResult bindingResult){
        try {
            userCredentialsService.register(registerRequest);
            return new ResponseEntity<>("register is sucessfull", HttpStatus.OK);
        } catch (ExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hi")
    String hi(@RequestHeader("Authorization") String token){
        return token;
    }
}
