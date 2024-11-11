package com.kaleidoscope.inventory.controller;

import com.kaleidoscope.inventory.dto.InventoryDTO;
import com.kaleidoscope.inventory.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class InventoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
    }

    @Test
    void getInventoryItems() throws Exception {
        List<InventoryDTO> inventoryList = Arrays.asList(
                new InventoryDTO(1, 101, "Red", "M", 50),
                new InventoryDTO(2, 102, "Blue", "L", 30)
        );
        when(inventoryService.getAllInventoryItems()).thenReturn(inventoryList);

        mockMvc.perform(get("/api/inventory/v1/getinventoryitems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].inventoryItemId").value(1))
                .andExpect(jsonPath("$[0].productId").value(101))
                .andExpect(jsonPath("$[0].color").value("Red"))
                .andExpect(jsonPath("$[0].size").value("M"))
                .andExpect(jsonPath("$[0].quantity").value(50))
                .andExpect(jsonPath("$[1].inventoryItemId").value(2))
                .andExpect(jsonPath("$[1].productId").value(102))
                .andExpect(jsonPath("$[1].color").value("Blue"))
                .andExpect(jsonPath("$[1].size").value("L"))
                .andExpect(jsonPath("$[1].quantity").value(30));

        verify(inventoryService, times(1)).getAllInventoryItems();
    }

    @Test
    void getInventoryItem() throws Exception {
        InventoryDTO inventoryItem = new InventoryDTO(1, 101, "Red", "M", 50);
        when(inventoryService.getInventoryItemById(1)).thenReturn(inventoryItem);

        mockMvc.perform(get("/api/inventory/v1/getinventoryitem/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryItemId").value(1))
                .andExpect(jsonPath("$.productId").value(101))
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.size").value("M"))
                .andExpect(jsonPath("$.quantity").value(50));

        verify(inventoryService, times(1)).getInventoryItemById(1);
    }

    @Test
    void saveInventoryItem() throws Exception {
        InventoryDTO inventoryItem = new InventoryDTO(1, 101, "Red", "M", 50);
        when(inventoryService.saveInventoryItem(any(InventoryDTO.class))).thenReturn(inventoryItem);

        mockMvc.perform(post("/api/inventory/v1/addinventoryitem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"inventoryItemId\":1,\"productId\":101,\"color\":\"Red\",\"size\":\"M\",\"quantity\":50}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryItemId").value(1))
                .andExpect(jsonPath("$.productId").value(101))
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.size").value("M"))
                .andExpect(jsonPath("$.quantity").value(50));

        verify(inventoryService, times(1)).saveInventoryItem(any(InventoryDTO.class));
    }

    @Test
    void updateInventoryItem() throws Exception {
        InventoryDTO updatedItem = new InventoryDTO(1, 101, "Blue", "L", 40);
        when(inventoryService.updateInventoryItem(any(InventoryDTO.class))).thenReturn(updatedItem);

        mockMvc.perform(put("/api/inventory/v1/updateinventoryitem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"inventoryItemId\":1,\"productId\":101,\"color\":\"Blue\",\"size\":\"L\",\"quantity\":40}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryItemId").value(1))
                .andExpect(jsonPath("$.productId").value(101))
                .andExpect(jsonPath("$.color").value("Blue"))
                .andExpect(jsonPath("$.size").value("L"))
                .andExpect(jsonPath("$.quantity").value(40));

        verify(inventoryService, times(1)).updateInventoryItem(any(InventoryDTO.class));
    }

    @Test
    void deleteInventoryItem() throws Exception {
        when(inventoryService.deleteInventoryItem(1)).thenReturn("Item deleted successfully");

        mockMvc.perform(delete("/api/inventory/v1/deleteinventoryitem/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Item deleted successfully"));

        verify(inventoryService, times(1)).deleteInventoryItem(1);
    }
}
