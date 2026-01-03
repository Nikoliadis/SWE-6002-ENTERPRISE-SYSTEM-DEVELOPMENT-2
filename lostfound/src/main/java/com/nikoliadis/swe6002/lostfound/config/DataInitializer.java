package com.nikoliadis.swe6002.lostfound.config;

import com.nikoliadis.swe6002.lostfound.model.User;
import com.nikoliadis.swe6002.lostfound.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository,
                                PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByUsername("admin").isEmpty()) {

                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@lostfound.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");

                userRepository.save(admin);

                System.out.println("✅ Admin user created");
            }
        };
    }
}
