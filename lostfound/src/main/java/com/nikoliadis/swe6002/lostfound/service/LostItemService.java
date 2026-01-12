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
                    String contactEmail,
                    String contactPhone,
                    MultipartFile image,
                    User user);

    void update(Long id,
                String title,
                String description,
                String location,
                ItemType type,
                String contactEmail,
                String contactPhone,
                MultipartFile image);

    List<LostItem> findAll();

    List<LostItem> findAllSorted();

    List<LostItem> findByType(ItemType type);

    List<LostItem> findByStatus(ItemStatus status);

    List<LostItem> findByTypeAndStatus(ItemType type, ItemStatus status);

    List<LostItem> findByUser(User user);

    Optional<LostItem> findById(Long id);

    void deleteById(Long id);

    void toggleStatus(Long id);

    List<LostItem> search(String q);
}
