package logistic.apilogistic.controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class registerController {
    private final AuthenticationManager authenticationManager;

    public registerController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping()
    String getinfo(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        return "hello";
    }
}
