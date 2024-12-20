package com.kaleidoscope.inventory.controller;

import com.kaleidoscope.inventory.dto.InventoryDTO;
import com.kaleidoscope.inventory.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturnTestMessage_whenTestEndpointIsCalled() throws Exception {
        mockMvc.perform(get("/api/v1/inventory/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Inventory is working"));
    }

    @Test
    void shouldReturnAllInventoryItems_whenGetInventoryItemsIsCalled() throws Exception {
        InventoryDTO inventoryDTO = new InventoryDTO("sku123", "Product A", 10, 100.0);
        when(inventoryService.getAllInventoryItems()).thenReturn(Collections.singletonList(inventoryDTO));

        mockMvc.perform(get("/api/v1/inventory/getinventoryitems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sku").value("sku123"))
                .andExpect(jsonPath("$[0].productName").value("Product A"))
                .andExpect(jsonPath("$[0].quantity").value(10));
    }

    @Test
    void shouldReturnInventoryItem_whenValidSkuIsGiven() throws Exception {
        InventoryDTO inventoryDTO = new InventoryDTO("sku123", "Product A", 10, 100.0);
        when(inventoryService.getInventoryItemBySku("sku123")).thenReturn(inventoryDTO);

        mockMvc.perform(get("/api/v1/inventory/getinventoryitem/{sku}", "sku123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("sku123"))
                .andExpect(jsonPath("$.productName").value("Product A"));
    }

    @Test
    void shouldReturnEmptyList_whenNoInventoryItemsExist() throws Exception {
        when(inventoryService.getAllInventoryItems()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/inventory/getinventoryitems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shouldReturnNotFound_whenInventoryItemDoesNotExist() throws Exception {
        when(inventoryService.getInventoryItemBySku("sku123")).thenReturn(null);

        mockMvc.perform(get("/api/v1/inventory/getinventoryitem/{sku}", "sku123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateInventoryItem_whenValidItemIsPosted() throws Exception {
        InventoryDTO inventoryDTO = new InventoryDTO("sku123", "Product A", 10, 100.0);
        when(inventoryService.saveInventoryItem(any(InventoryDTO.class))).thenReturn(inventoryDTO);

        mockMvc.perform(post("/api/v1/inventory/addinventoryitem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sku").value("sku123"))
                .andExpect(jsonPath("$.productName").value("Product A"));
    }

    @Test
    void shouldUpdateInventoryItem_whenValidItemIsPosted() throws Exception {
        InventoryDTO inventoryDTO = new InventoryDTO("sku123", "Updated Product", 20, 150.0);
        when(inventoryService.updateInventoryItem(any(InventoryDTO.class))).thenReturn(inventoryDTO);

        mockMvc.perform(put("/api/v1/inventory/updateinventoryitem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("sku123"))
                .andExpect(jsonPath("$.productName").value("Updated Product"));
    }



    @Test
    void shouldDeleteInventoryItem_whenValidSkuIsGiven() throws Exception {
        when(inventoryService.deleteInventoryItem("sku123")).thenReturn("Item deleted successfully");

        mockMvc.perform(delete("/api/v1/inventory/deleteinventoryitem/{sku}", "sku123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Item deleted successfully"));
    }

    @Test
    void shouldReturnErrorMessage_whenDeletingNonExistingItem() throws Exception {
        when(inventoryService.deleteInventoryItem("sku123")).thenReturn("Item not found");

        mockMvc.perform(delete("/api/v1/inventory/deleteinventoryitem/{sku}", "sku123"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Item not found"));
    }
}
