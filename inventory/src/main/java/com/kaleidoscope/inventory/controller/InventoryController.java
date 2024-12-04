package com.kaleidoscope.inventory.controller;

import com.kaleidoscope.inventory.dto.InventoryDTO;
import com.kaleidoscope.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController

@RequestMapping(value = "api/v1/inventory/")

public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/test")
    public Mono<String> testItems() {
        return Mono.just("Inventory is working");
    }

    @GetMapping("/getinventoryitems")
    public List<InventoryDTO> getInventoryItems() {
        return inventoryService.getAllInventoryItems();
    }

    @GetMapping("/getinventoryitem/{sku}")
    public InventoryDTO getInventoryItem(@PathVariable String sku) {
        return inventoryService.getInventoryItemBySku(sku);
    }

    @PostMapping("/addinventoryitem")
    public InventoryDTO saveInventoryItem(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.saveInventoryItem(inventoryDTO);
    }

    @PutMapping("/updateinventoryitem")
    public InventoryDTO updateInventoryItem(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.updateInventoryItem(inventoryDTO);
    }

    @DeleteMapping("/deleteinventoryitem/{sku}")
    public String deleteInventoryItem(@PathVariable String sku) {
        return inventoryService.deleteInventoryItem(sku);
    }
}
