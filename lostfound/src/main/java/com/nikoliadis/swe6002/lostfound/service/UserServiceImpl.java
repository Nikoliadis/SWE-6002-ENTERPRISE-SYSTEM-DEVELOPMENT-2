package com.nikoliadis.swe6002.lostfound.service;

import com.nikoliadis.swe6002.lostfound.model.RegisterForm;
import com.nikoliadis.swe6002.lostfound.model.User;
import com.nikoliadis.swe6002.lostfound.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(RegisterForm form) {
        if (userRepository.existsByUsername(form.getUsername())) {
            return "Username already exists";
        }
        if (userRepository.existsByEmail(form.getEmail())) {
            return "Email already exists";
        }
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            return "Passwords do not match";
        }

        User user = new User();
        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);
        return null; // null = success
    }
}
