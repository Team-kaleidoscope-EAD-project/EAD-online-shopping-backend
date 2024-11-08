package org.kaleidoscope.inventory.controller;

import org.kaleidoscope.inventory.dto.InventoryDTO;
import org.kaleidoscope.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/inventory/v1/")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/getinventoryitems")
    public List<InventoryDTO> getInventoryItems() {
        return inventoryService.getAllInventoryItems();
    }

    @GetMapping("/getinventoryitem/{inventoryItemId}")
    public InventoryDTO getInventoryItem(@PathVariable Integer inventoryItemId) {
        return inventoryService.getInventoryItemById(inventoryItemId);
    }

    @PostMapping("/addinventoryitem")
    public InventoryDTO saveInventoryItem(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.saveInventoryItem(inventoryDTO);
    }

    @PutMapping("/updateinventoryitem")
    public InventoryDTO updateInventoryItem(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.updateInventoryItem(inventoryDTO);
    }

    @DeleteMapping("/deleteinventoryitem/{inventoryItemId}")
    public String deleteInventoryItem(@PathVariable Integer inventoryItemId) {
        return inventoryService.deleteInventoryItem(inventoryItemId);
    }
}
