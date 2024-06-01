package logistic.apilogistic.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import logistic.apilogistic.authRequest.LoginRequest;
import logistic.apilogistic.authRequest.RegisterRequest;
import logistic.apilogistic.exceptions.ExistsException;
import logistic.apilogistic.service.LoginService;
import logistic.apilogistic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {
    private final UserService userService;
    private final LoginService loginService;

    @Autowired
    public AuthController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws JsonProcessingException {
        String token = loginService.authenticateAndCreateToken(loginRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            userService.register(registerRequest);
            return new ResponseEntity<>("register is sucessfull", HttpStatus.OK);
        } catch (ExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
