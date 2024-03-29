package logistic.apilogistic.controllers;

import logistic.apilogistic.authRequest.LoginRequest;
import logistic.apilogistic.config.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class registerController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public registerController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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

    @GetMapping("/hi")
    String hi(@RequestHeader("Authorization") String token){
        return token;
    }
}
