package com.packt.cardatabase.web;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.packt.cardatabase.domain.AppUser;
import com.packt.cardatabase.domain.RegistrationRequest;
import com.packt.cardatabase.service.RegistrationService;

@RestController
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        try {
            AppUser newUser = registrationService.registerNewUser(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new RegistrationResponse (
                            "User registered successfully",
                            newUser.getUsername()
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred during registration"));
        }
    }

    private record RegistrationResponse(String message, String username) {}
    private record ErrorResponse(String error){}

}
