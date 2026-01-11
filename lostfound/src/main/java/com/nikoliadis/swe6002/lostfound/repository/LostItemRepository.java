package com.nikoliadis.swe6002.lostfound.repository;

import com.nikoliadis.swe6002.lostfound.model.ItemType;
import com.nikoliadis.swe6002.lostfound.model.LostItem;
import com.nikoliadis.swe6002.lostfound.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {

    List<LostItem> findAllByOrderByCreatedAtDesc();

    List<LostItem> findByTypeOrderByCreatedAtDesc(ItemType type);

    List<LostItem> findByUserOrderByCreatedAtDesc(User user);
}
