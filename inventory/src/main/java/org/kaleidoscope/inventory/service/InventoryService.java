package org.kaleidoscope.inventory.service;

import org.kaleidoscope.inventory.dto.InventoryDTO;
import org.kaleidoscope.inventory.model.InventoryItem;
import org.kaleidoscope.inventory.repo.InventoryRepo;
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

    public InventoryDTO getInventoryItemById(Integer inventoryItemId) {
        InventoryItem inventoryItem = inventoryRepo.findById(inventoryItemId).orElse(null);
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

    public String deleteInventoryItem(Integer inventoryItemId) {
        inventoryRepo.deleteById(inventoryItemId);
        return "Inventory Item deleted successfully";
    }
}
