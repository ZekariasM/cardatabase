package com.packt.cardatabase.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.packt.cardatabase.domain.AppUser;
import com.packt.cardatabase.domain.RegistrationRequest;
import com.packt.cardatabase.service.RegistrationService;

/**
 * REST Controller for user registration
 *
 * Handles user registration requests.
 * Error handling is managed by GlobalExceptionHandler.
 */
@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Register a new user
     *
     * @param request - validated registration request with username and password
     * @return 201 CREATED with success message
     *
     * Note: No try-catch needed - GlobalExceptionHandler handles all exceptions:
     * - Validation errors (@Valid) → 400 BAD REQUEST
     * - IllegalArgumentException (username taken) → 400 BAD REQUEST
     * - Other exceptions → 500 INTERNAL SERVER ERROR
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        // Call service to register user
        // Any exceptions will be caught by GlobalExceptionHandler
        AppUser newUser = registrationService.registerNewUser(request);

        // Return success response
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegistrationResponse(
                        "User registered successfully",
                        newUser.getUsername()
                ));
    }

    /**
     * Response record for successful registration
     */
    private record RegistrationResponse(String message, String username) {}
}
