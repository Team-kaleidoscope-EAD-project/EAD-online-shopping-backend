package com.kaleidoscope.inventory.service;

import com.kaleidoscope.inventory.dto.InventoryDTO;
import com.kaleidoscope.inventory.model.InventoryItem;
import com.kaleidoscope.inventory.repo.InventoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
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

    @Test
    void testGetAllInventoryItems() {
        //Arrange
        InventoryItem item = new InventoryItem("sku1", 10, LocalDateTime.now());
        when(inventoryRepo.findAll()).thenReturn(Arrays.asList(item));
        when(modelMapper.map(any(), any())).thenReturn(new InventoryDTO("sku1", 10, LocalDateTime.now()));
        //Act
        List<InventoryDTO> result = inventoryService.getAllInventoryItems();
        //Assert
        assertEquals(1, result.size());
        assertEquals("sku1", result.get(0).getSku());
    }

    @Test
    void testGetInventoryItemBySku() {
        // Arrange
        String sku = "sku1";
        InventoryItem item = new InventoryItem(sku, 15, LocalDateTime.now());
        InventoryDTO dto = new InventoryDTO(sku, 15, LocalDateTime.now());

        when(inventoryRepo.findBySku(sku)).thenReturn(item);
        when(modelMapper.map(item, InventoryDTO.class)).thenReturn(dto);

        // Act
        InventoryDTO result = inventoryService.getInventoryItemBySku(sku);

        // Assert
        assertNotNull(result);
        assertEquals(sku, result.getSku());
        assertEquals(15, result.getQuantity());
    }

    @Test
    void testSaveInventoryItem() {
        InventoryDTO dto = new InventoryDTO("sku1", 20, LocalDateTime.now());
        InventoryItem item = new InventoryItem("sku1", 20, LocalDateTime.now());

        when(modelMapper.map(dto, InventoryItem.class)).thenReturn(item);
        when(inventoryRepo.save(item)).thenReturn(item);

        InventoryDTO result = inventoryService.saveInventoryItem(dto);

        assertNotNull(result);
        assertEquals("sku1", result.getSku());
    }


    @Test
    void testUpdateInventoryItem() {
        // Arrange
        InventoryDTO dto = new InventoryDTO("sku1", 25, LocalDateTime.now());
        InventoryItem item = new InventoryItem("sku1", 25, LocalDateTime.now());

        when(modelMapper.map(dto, InventoryItem.class)).thenReturn(item);
        when(inventoryRepo.save(item)).thenReturn(item);

        // Act
        InventoryDTO result = inventoryService.updateInventoryItem(dto);

        // Assert
        assertNotNull(result);
        assertEquals("sku1", result.getSku());
        assertEquals(25, result.getQuantity());
    }

    @Test
    void testDeleteInventoryItem() {
        // Arrange
        String sku = "sku1";

        doNothing().when(inventoryRepo).deleteBySku(sku);

        // Act
        String result = inventoryService.deleteInventoryItem(sku);

        // Assert
        assertEquals("Inventory Item deleted successfully", result);
        verify(inventoryRepo, times(1)).deleteBySku(sku);
    }
}

