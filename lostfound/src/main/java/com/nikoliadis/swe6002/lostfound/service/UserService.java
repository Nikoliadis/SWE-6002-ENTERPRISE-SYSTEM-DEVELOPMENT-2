package com.nikoliadis.swe6002.lostfound.service;

import com.nikoliadis.swe6002.lostfound.model.RegisterForm;
import com.nikoliadis.swe6002.lostfound.model.User;

import java.util.List;

public interface UserService {
    String register(RegisterForm form);

    // ADMIN
    List<User> findAllUsers();
    void toggleRole(Long userId);
    void deleteUser(Long userId, String currentUsername);
}
