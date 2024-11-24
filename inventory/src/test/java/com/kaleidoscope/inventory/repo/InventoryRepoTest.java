package com.kaleidoscope.inventory.repo;

import com.kaleidoscope.inventory.model.InventoryItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class InventoryRepoTest {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Test
    public void testSaveInventoryItem() {
        InventoryItem item = new InventoryItem(0, 101, "Red", "M", 10);

        InventoryItem savedItem = inventoryRepo.save(item);

        assertNotNull(savedItem);
        assertNotNull(savedItem.getInventoryItemId());
        assertEquals(101, savedItem.getProductId());
        assertEquals("Red", savedItem.getColor());
    }

    @Test
    public void testFindById() {
        InventoryItem item = new InventoryItem(0, 102, "Blue", "L", 20);

        InventoryItem savedItem = inventoryRepo.save(item);

        Optional<InventoryItem> retrievedItem = inventoryRepo.findById(savedItem.getInventoryItemId());

        assertTrue(retrievedItem.isPresent());
        assertEquals("Blue", retrievedItem.get().getColor());
        assertEquals(20, retrievedItem.get().getQuantity());
    }

    @Test
    public void testFindAll() {
        InventoryItem item1 = new InventoryItem(0, 103, "Green", "S", 15);
        InventoryItem item2 = new InventoryItem(0, 104, "Yellow", "M", 25);

        inventoryRepo.save(item1);
        inventoryRepo.save(item2);

        List<InventoryItem> items = inventoryRepo.findAll();

        assertNotNull(items);
        assertEquals(2, items.size());
    }

    @Test
    public void testDeleteById() {
        InventoryItem item = new InventoryItem(0, 105, "Black", "XL", 5);

        InventoryItem savedItem = inventoryRepo.save(item);

        inventoryRepo.deleteById(savedItem.getInventoryItemId());

        Optional<InventoryItem> retrievedItem = inventoryRepo.findById(savedItem.getInventoryItemId());

        assertFalse(retrievedItem.isPresent());
    }

    @Test
    public void testUpdateInventoryItem() {
        InventoryItem item = new InventoryItem(0, 106, "Purple", "L", 12);

        InventoryItem savedItem = inventoryRepo.save(item);

        savedItem.setQuantity(20);
        InventoryItem updatedItem = inventoryRepo.save(savedItem);

        assertEquals(20, updatedItem.getQuantity());
    }
}



