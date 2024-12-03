package com.kaleidoscope.inventory.controller;

import com.kaleidoscope.inventory.dto.InventoryDTO;
import com.kaleidoscope.inventory.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToController;

@WebFluxTest(InventoryController.class)  // This will load only the controller for testing
public class InventoryControllerTest {

    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private WebTestClient webTestClient;

    private InventoryDTO inventoryItem;

    @BeforeEach
    void setUp() {
        // Setup mock inventory item with LocalDateTime for lastRestockedAt
        inventoryItem = new InventoryDTO("sku123", 10, LocalDateTime.now());
    }

    @Test
    void testTestItems() {
        webTestClient.get()
                .uri("/api/v1/inventory/test")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Inventory is working");
    }

    @Test
    void testGetInventoryItems() {
        List<InventoryDTO> inventoryItems = Arrays.asList(
                new InventoryDTO("sku123", 10, LocalDateTime.now().minusDays(1)),
                new InventoryDTO("sku456", 20, LocalDateTime.now().minusDays(2))
        );

        when(inventoryService.getAllInventoryItems()).thenReturn(inventoryItems);

        webTestClient.get()
                .uri("/api/v1/inventory/getinventoryitems")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(InventoryDTO.class)
                .hasSize(2)
                .contains(inventoryItem);  // Ensure InventoryDTO matches
    }

    @Test
    void testGetInventoryItemBySku() {
        when(inventoryService.getInventoryItemBySku("sku123")).thenReturn(inventoryItem);

        webTestClient.get()
                .uri("/api/v1/inventory/getinventoryitem/{sku}", "sku123")
                .exchange()
                .expectStatus().isOk()
                .expectBody(InventoryDTO.class)
                .value(inventoryDTO -> {
                    // Assert inventoryDTO properties correctly, especially the lastRestockedAt as LocalDateTime
                    assert inventoryDTO.getSku().equals("sku123");
                    assert inventoryDTO.getQuantity() == 10;
                    assert inventoryDTO.getLastRestockedAt() != null;  // Make sure it's properly set
                });
    }

    @Test
    void testSaveInventoryItem() {
        when(inventoryService.saveInventoryItem(inventoryItem)).thenReturn(inventoryItem);

        webTestClient.post()
                .uri("/api/v1/inventory/addinventoryitem")
                .bodyValue(inventoryItem)
                .exchange()
                .expectStatus().isOk()
                .expectBody(InventoryDTO.class)
                .isEqualTo(inventoryItem);
    }

    @Test
    void testUpdateInventoryItem() {
        when(inventoryService.updateInventoryItem(inventoryItem)).thenReturn(inventoryItem);

        webTestClient.put()
                .uri("/api/v1/inventory/updateinventoryitem")
                .bodyValue(inventoryItem)
                .exchange()
                .expectStatus().isOk()
                .expectBody(InventoryDTO.class)
                .isEqualTo(inventoryItem);
    }

    @Test
    void testDeleteInventoryItem() {
        when(inventoryService.deleteInventoryItem("sku123")).thenReturn("Item deleted successfully");

        webTestClient.delete()
                .uri("/api/v1/inventory/deleteinventoryitem/{sku}", "sku123")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Item deleted successfully");
    }
}
