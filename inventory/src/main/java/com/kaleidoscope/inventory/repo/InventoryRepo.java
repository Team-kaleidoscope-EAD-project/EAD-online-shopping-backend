package com.kaleidoscope.inventory.repo;

import com.kaleidoscope.inventory.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<InventoryItem, String> {
    InventoryItem findBySku(String sku);
    void deleteBySku(String sku);
}

