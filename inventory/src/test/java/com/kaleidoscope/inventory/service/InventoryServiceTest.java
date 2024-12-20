package com.kaleidoscope.inventory.service;

import com.kaleidoscope.inventory.dto.InventoryDTO;
import com.kaleidoscope.inventory.dto.UpdateDto;
import com.kaleidoscope.inventory.model.InventoryItem;
import com.kaleidoscope.inventory.repo.InventoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @Mock
    private InventoryRepo inventoryRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test cases for getAllInventoryItems()
    @Test
    void shouldReturnAllInventoryItems() {
        // Given
        List<InventoryItem> inventoryItems = Arrays.asList(
                new InventoryItem("sku123", "Product A", 10, 100.0),
                new InventoryItem("sku124", "Product B", 20, 150.0)
        );
        when(inventoryRepo.findAll()).thenReturn(inventoryItems);
        when(modelMapper.map(inventoryItems, new TypeToken<List<InventoryDTO>>() {}.getType())).thenReturn(Arrays.asList(
                new InventoryDTO("sku123", "Product A", 10, 100.0),
                new InventoryDTO("sku124", "Product B", 20, 150.0)
        ));

        // When
        List<InventoryDTO> result = inventoryService.getAllInventoryItems();

        // Then
        assertEquals(2, result.size());
        assertEquals("Product A", result.get(0).getProductName());
    }

    @Test
    void shouldReturnEmptyListWhenNoInventoryItemsExist() {
        // Given
        when(inventoryRepo.findAll()).thenReturn(Collections.emptyList());

        // When
        List<InventoryDTO> result = inventoryService.getAllInventoryItems();

        // Then
        assertTrue(result.isEmpty());
    }

    // Test cases for getInventoryItemBySku()
    @Test
    void shouldReturnInventoryItemBySku_whenSkuExists() {
        // Given
        InventoryItem inventoryItem = new InventoryItem("sku123", "Product A", 10, 100.0);
        when(inventoryRepo.findBySku("sku123")).thenReturn(inventoryItem);
        when(modelMapper.map(inventoryItem, InventoryDTO.class)).thenReturn(new InventoryDTO("sku123", "Product A", 10, 100.0));

        // When
        InventoryDTO result = inventoryService.getInventoryItemBySku("sku123");

        // Then
        assertNotNull(result);
        assertEquals("Product A", result.getProductName());
    }

    @Test
    void shouldThrowException_whenSkuNotFound() {
        // Given
        when(inventoryRepo.findBySku("invalid-sku")).thenReturn(null);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.getInventoryItemBySku("invalid-sku");
        });
    }

    // Test cases for saveInventoryItem()
    @Test
    void shouldSaveInventoryItemSuccessfully() {
        // Given
        InventoryDTO inventoryDTO = new InventoryDTO("sku123", "Product A", 10, 100.0);
        InventoryItem inventoryItem = new InventoryItem("sku123", "Product A", 10, 100.0);
        when(inventoryRepo.save(any(InventoryItem.class))).thenReturn(inventoryItem);

        // When
        InventoryDTO result = inventoryService.saveInventoryItem(inventoryDTO);

        // Then
        assertEquals("Product A", result.getProductName());
        assertEquals(10, result.getQuantity());
    }

    @Test
    void shouldThrowException_whenSavingInvalidInventoryItem() {
        // Given
        InventoryDTO inventoryDTO = new InventoryDTO("", "Product A", 10, 100.0);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.saveInventoryItem(inventoryDTO);
        });
    }

    // Test cases for updateInventoryItem()
    @Test
    void shouldUpdateInventoryItemSuccessfully() {
        // Given
        InventoryDTO inventoryDTO = new InventoryDTO("sku123", "Product A", 15, 100.0);
        InventoryItem updatedItem = new InventoryItem("sku123", "Product A", 15, 100.0);
        when(inventoryRepo.save(any(InventoryItem.class))).thenReturn(updatedItem);

        // When
        InventoryDTO result = inventoryService.updateInventoryItem(inventoryDTO);

        // Then
        assertEquals("Product A", result.getProductName());
        assertEquals(15, result.getQuantity());
    }

    @Test
    void shouldThrowException_whenUpdatingInvalidInventoryItem() {
        // Given
        InventoryDTO inventoryDTO = new InventoryDTO("sku123", "", 10, 100.0);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.updateInventoryItem(inventoryDTO);
        });
    }

    // Test cases for deleteInventoryItem()
    @Test
    void shouldDeleteInventoryItemSuccessfully() {
        // Given
        String sku = "sku123";
        doNothing().when(inventoryRepo).deleteBySku(sku);

        // When
        String result = inventoryService.deleteInventoryItem(sku);

        // Then
        assertEquals("Inventory Item deleted successfully", result);
    }

    @Test
    void shouldThrowException_whenDeletingNonExistingInventoryItem() {
        // Given
        String sku = "nonexistent-sku";
        doThrow(new IllegalArgumentException("Item not found")).when(inventoryRepo).deleteBySku(sku);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.deleteInventoryItem(sku);
        });
    }

    // Test cases for updateInventory()
    @Test
    void shouldUpdateInventoryQuantitySuccessfully() {
        // Given
        UpdateDto updateDto = new UpdateDto("sku123", 5);
        InventoryItem inventoryItem = new InventoryItem("sku123", "Product A", 10, 100.0);
        when(inventoryRepo.findBySku("sku123")).thenReturn(inventoryItem);
        when(inventoryRepo.save(inventoryItem)).thenReturn(inventoryItem);

        // When
        InventoryDTO result = inventoryService.updateInventory(updateDto);

        // Then
        assertEquals(5, result.getQuantity());
    }

    @Test
    void shouldThrowException_whenInsufficientStock() {
        // Given
        UpdateDto updateDto = new UpdateDto("sku123", 15);  // Request more than available
        InventoryItem inventoryItem = new InventoryItem("sku123", "Product A", 10, 100.0);
        when(inventoryRepo.findBySku("sku123")).thenReturn(inventoryItem);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.updateInventory(updateDto);
        });
    }
}
