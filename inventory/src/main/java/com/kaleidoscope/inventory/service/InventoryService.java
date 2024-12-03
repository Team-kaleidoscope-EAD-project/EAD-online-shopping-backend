package com.kaleidoscope.inventory.service;

import com.kaleidoscope.inventory.dto.InventoryDTO;
import com.kaleidoscope.inventory.model.InventoryItem;
import com.kaleidoscope.inventory.repo.InventoryRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kaleidoscope.inventory.dto.UpdateDto;

import java.util.List;

@Service
@Transactional
public class InventoryService {
    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

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


    public InventoryDTO updateInventory(UpdateDto updateDto) {

        InventoryItem inventoryItem = inventoryRepo.findBySku(updateDto.getSku());

        if (inventoryItem == null) {
            throw new IllegalArgumentException("Item with SKU " + updateDto.getSku() + " not found");
        }

        int updatedQuantity = inventoryItem.getQuantity() - updateDto.getQuantity();
        if (updatedQuantity < 0) {
            throw new IllegalArgumentException("Insufficient stock for SKU " + updateDto.getSku());
        }
        inventoryItem.setQuantity(updatedQuantity);

        inventoryRepo.save(inventoryItem);

        return modelMapper.map(inventoryItem, InventoryDTO.class);
    }

}
