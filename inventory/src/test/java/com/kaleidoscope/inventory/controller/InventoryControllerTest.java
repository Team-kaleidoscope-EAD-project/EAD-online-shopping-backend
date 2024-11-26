//package com.kaleidoscope.inventory.controller;
//
//import com.kaleidoscope.inventory.dto.InventoryDTO;
//import com.kaleidoscope.inventory.service.InventoryService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(InventoryController.class)
//public class InventoryControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc; //simulates HTTP requests
//
//    @MockBean
//    private InventoryService inventoryService; // Mocks InventoryServices
//
//    @Autowired
//    private ObjectMapper objectMapper; // Convert object to JSON
//
//    @Test
//    public void testGetInventoryItems() throws Exception {
//        List<InventoryDTO> items = Arrays.asList(
//                new InventoryDTO(1, 101, "Red", "M", 10),
//                new InventoryDTO(2, 102, "Blue", "L", 20)
//        );
//
//        when(inventoryService.getAllInventoryItems()).thenReturn(items); // Mock the service call
//
//        mockMvc.perform(get("/api/v1/inventory/getinventoryitems"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].color").value("Red"))
//                .andExpect(jsonPath("$[1].size").value("L"));
//    }
//
//    @Test
//    public void testGetInventoryItem() throws Exception {
//        InventoryDTO item = new InventoryDTO(1, 101, "Red", "M", 10);
//
//        when(inventoryService.getInventoryItemById(1)).thenReturn(item);
//
//        mockMvc.perform(get("/api/v1/inventory/getinventoryitem/{inventoryItemId}", 1))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.color").value("Red"))
//                .andExpect(jsonPath("$.quantity").value(10));
//    }
//
//    @Test
//    public void testSaveInventoryItem() throws Exception {
//        InventoryDTO dto = new InventoryDTO(0, 103, "Green", "S", 30);
//
//        when(inventoryService.saveInventoryItem(any(InventoryDTO.class))).thenReturn(dto);
//
//        mockMvc.perform(post("/api/v1/inventory/addinventoryitem")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.color").value("Green"))
//                .andExpect(jsonPath("$.size").value("S"))
//                .andExpect(jsonPath("$.quantity").value(30));
//    }
//
//    @Test
//    public void testUpdateInventoryItem() throws Exception {
//        InventoryDTO dto = new InventoryDTO(1, 104, "Blue", "M", 50);
//
//        when(inventoryService.updateInventoryItem(any(InventoryDTO.class))).thenReturn(dto);
//
//        mockMvc.perform(put("/api/v1/inventory/updateinventoryitem")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.productId").value(104))
//                .andExpect(jsonPath("$.quantity").value(50));
//    }
//
//    @Test
//    public void testDeleteInventoryItem() throws Exception {
//        when(inventoryService.deleteInventoryItem(1)).thenReturn("Inventory Item deleted successfully");
//
//        mockMvc.perform(delete("/api/v1/inventory/deleteinventoryitem/{inventoryItemId}", 1))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Inventory Item deleted successfully"));
//    }
//}
//
//
