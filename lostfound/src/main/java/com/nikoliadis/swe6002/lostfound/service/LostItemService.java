package com.nikoliadis.swe6002.lostfound.service;

import com.nikoliadis.swe6002.lostfound.model.ItemStatus;
import com.nikoliadis.swe6002.lostfound.model.ItemType;
import com.nikoliadis.swe6002.lostfound.model.LostItem;
import com.nikoliadis.swe6002.lostfound.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface LostItemService {

    LostItem create(String title,
                    String description,
                    String location,
                    ItemType type,
                    MultipartFile image,
                    User user);

    List<LostItem> findAll();

    List<LostItem> findAllSorted();

    List<LostItem> findByType(ItemType type);

    List<LostItem> findByStatus(ItemStatus status);

    List<LostItem> findByTypeAndStatus(ItemType type, ItemStatus status);

    List<LostItem> findByUser(User user);

    List<LostItem> search(String q);

    void deleteById(Long id);

    Optional<LostItem> findById(Long id);

    LostItem update(Long id,
                    String title,
                    String description,
                    String location,
                    ItemType type);


    void toggleStatus(Long id);
}
