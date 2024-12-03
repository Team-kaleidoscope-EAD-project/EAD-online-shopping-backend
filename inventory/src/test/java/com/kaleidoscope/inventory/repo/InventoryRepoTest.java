package com.kaleidoscope.inventory.repo;

import com.kaleidoscope.inventory.model.InventoryItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InventoryRepoTest {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Test
    void testFindBySku() {
        //Arrange
        InventoryItem item = new InventoryItem("sku1", 50, LocalDateTime.now());
        inventoryRepo.save(item);
        //Act
        InventoryItem foundItem = inventoryRepo.findBySku("sku1");
        //Assert
        assertNotNull(foundItem);
        assertEquals("sku1", foundItem.getSku());
    }

    @Test
    void testDeleteBySku() {
        //Arrange
        InventoryItem item = new InventoryItem("sku2", 100, LocalDateTime.now());
        inventoryRepo.save(item);
        //Act
        inventoryRepo.deleteBySku("sku2");
        InventoryItem deletedItem = inventoryRepo.findBySku("sku2");
        //Assert
        assertNull(deletedItem);
    }
}

