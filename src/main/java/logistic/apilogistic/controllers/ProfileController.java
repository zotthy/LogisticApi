package logistic.apilogistic.controllers;

import logistic.apilogistic.entity.Address;
import logistic.apilogistic.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/profile/address")
    public ResponseEntity<?> createAddress(@RequestHeader("Authorization") String token,
                                           @RequestBody Address addressDto) {
        profileService.createAndAssignAddressToUser(token, addressDto);
        return ResponseEntity.ok().body("Address created successfully.");
    }
    @GetMapping("/profile")
    public ResponseEntity<?> getProfileInfo(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok().body(profileService.getProfile(token));
    }
    @GetMapping("/checkProfile/{email}")
    public ResponseEntity<?> checkProfile(@PathVariable String email){
        return ResponseEntity.ok().body(profileService.checkProfile(email));
    }
}
