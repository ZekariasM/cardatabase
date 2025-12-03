package com.packt.cardatabase.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.packt.cardatabase.domain.AppUser;
import com.packt.cardatabase.domain.AppUserRepository;
import com.packt.cardatabase.domain.RegistrationRequest;

public class RegistrationServiceTest {
    @Mock
    private AppUserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private RegistrationService registrationService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterNewUser_Success() {
        RegistrationRequest request = new RegistrationRequest("john", "password123");

        when(userRepository.existsByUsername("john")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassed");
        when(userRepository.save(any(AppUser.class))).thenReturn(
                new AppUser("john", "hashedPassword", "USER")
        );

        AppUser result = registrationService.registerNewUser(request);

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        assertEquals("USER", result.getRole());
        verify(userRepository).save(any(AppUser.class));
    }

    @Test
    void testRegisterNewUser_UsernameTaken() {
        RegistrationRequest request = new RegistrationRequest("john", "password123");

        when(userRepository.existsByUsername("john")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            registrationService.registerNewUser(request);
        });

        verify(userRepository, never()).save(any(AppUser.class));
    }

}
