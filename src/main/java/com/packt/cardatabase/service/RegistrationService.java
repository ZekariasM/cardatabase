package com.packt.cardatabase.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.packt.cardatabase.domain.AppUser;
import com.packt.cardatabase.domain.AppUserRepository;
import com.packt.cardatabase.domain.RegistrationRequest;

@Service
public class RegistrationService {
	private final AppUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public RegistrationService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public AppUser registerNewUser(RegistrationRequest request) {
		if (userRepository.existsByUsername(request.username())) {
			throw new IllegalArgumentException(
					"username '" + request.username() + "' is already taken"
					);
		}
        String hashedPassword = passwordEncoder.encode(request.password());

        AppUser newUser = new AppUser(
                request.username(),
                hashedPassword,
                "User"
        );

        AppUser savedUser = userRepository.save(newUser);
        return savedUser;
	}
}

