package com.nikoliadis.swe6002.lostfound.config;

import com.nikoliadis.swe6002.lostfound.model.User;
import com.nikoliadis.swe6002.lostfound.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@lostfound.com");
            admin.setPassword(passwordEncoder.encode("Admin123!"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
        }
    }
}
