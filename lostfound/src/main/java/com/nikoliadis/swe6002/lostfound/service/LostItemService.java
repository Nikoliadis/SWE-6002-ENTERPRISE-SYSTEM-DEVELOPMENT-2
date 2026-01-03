package com.nikoliadis.swe6002.lostfound.service;

import com.nikoliadis.swe6002.lostfound.model.LostItem;
import com.nikoliadis.swe6002.lostfound.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LostItemService {

    LostItem create(String title, String description, String location, MultipartFile image, User user);

    List<LostItem> findByUser(User user);

    List<LostItem> findAll();

    void deleteById(Long id);
}
