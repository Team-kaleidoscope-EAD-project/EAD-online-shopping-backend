package com.kaleidoscope.inventory.service;

import com.kaleidoscope.inventory.dto.InventoryDTO;
import com.kaleidoscope.inventory.model.InventoryItem;
import com.kaleidoscope.inventory.repo.InventoryRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class InventoryService {
    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<InventoryDTO> getAllInventoryItems() {
        List<InventoryItem> inventoryItemList = inventoryRepo.findAll();
        return modelMapper.map(inventoryItemList, new TypeToken<List<InventoryDTO>>() {}.getType());
    }

    public InventoryDTO getInventoryItemBySku(String sku) {
        InventoryItem inventoryItem = inventoryRepo.findBySku(sku);
        return modelMapper.map(inventoryItem, InventoryDTO.class);
    }

    public InventoryDTO saveInventoryItem(InventoryDTO inventoryDTO) {
        inventoryRepo.save(modelMapper.map(inventoryDTO, InventoryItem.class));
        return inventoryDTO;
    }

    public InventoryDTO updateInventoryItem(InventoryDTO inventoryDTO) {
        inventoryRepo.save(modelMapper.map(inventoryDTO, InventoryItem.class));
        return inventoryDTO;
    }

    public String deleteInventoryItem(String sku) {
        inventoryRepo.deleteBySku(sku);
        return "Inventory Item deleted successfully";
    }
}
