package com.nikoliadis.swe6002.lostfound.service;

import com.nikoliadis.swe6002.lostfound.model.ItemType;
import com.nikoliadis.swe6002.lostfound.model.LostItem;
import com.nikoliadis.swe6002.lostfound.model.User;
import com.nikoliadis.swe6002.lostfound.repository.LostItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class LostItemServiceImpl implements LostItemService {

    private final LostItemRepository lostItemRepository;

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    public LostItemServiceImpl(LostItemRepository lostItemRepository) {
        this.lostItemRepository = lostItemRepository;
    }

    @Override
    public LostItem create(String title,
                           String description,
                           String location,
                           ItemType type,
                           MultipartFile image,
                           User user) {

        LostItem item = new LostItem();
        item.setTitle(title);
        item.setDescription(description);
        item.setLocation(location);
        item.setType(type);
        item.setUser(user);

        if (image != null && !image.isEmpty()) {
            String contentType = image.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("Only image files are allowed.");
            }

            String original = StringUtils.cleanPath(image.getOriginalFilename());
            String ext = "";
            int dot = original.lastIndexOf('.');
            if (dot >= 0) ext = original.substring(dot);

            String filename = UUID.randomUUID() + ext;

            try {
                Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
                Files.createDirectories(dir);

                Path target = dir.resolve(filename);
                Files.copy(image.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

                item.setImagePath("/uploads/" + filename);

            } catch (IOException e) {
                throw new RuntimeException("Failed to save image.", e);
            }
        }

        return lostItemRepository.save(item);
    }

    @Override
    public List<LostItem> findAll() {
        return lostItemRepository.findAll();
    }

    @Override
    public List<LostItem> findByUser(User user) {
        return lostItemRepository.findByUser(user);
    }

    @Override
    public void deleteById(Long id) {
        lostItemRepository.deleteById(id);
    }
}
