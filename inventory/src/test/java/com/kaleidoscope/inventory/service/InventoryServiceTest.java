//package com.kaleidoscope.inventory.service;
//
//import com.kaleidoscope.inventory.dto.InventoryDTO;
//import com.kaleidoscope.inventory.model.InventoryItem;
//import com.kaleidoscope.inventory.repo.InventoryRepo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.TypeToken;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class InventoryServiceTest {
//
//    @InjectMocks
//    private InventoryService inventoryService;
//
//    @Mock
//    private InventoryRepo inventoryRepo;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGetAllInventoryItems() {
//        List<InventoryItem> inventoryItems = Arrays.asList(
//                new InventoryItem(1, 101, "Red", "M", 10),
//                new InventoryItem(2, 102, "Blue", "L", 20)
//        );
//
//        List<InventoryDTO> inventoryDTOs = Arrays.asList(
//                new InventoryDTO(1, 101, "Red", "M", 10),
//                new InventoryDTO(2, 102, "Blue", "L", 20)
//        );
//
//        when(inventoryRepo.findAll()).thenReturn(inventoryItems);
//        when(modelMapper.map(inventoryItems, new TypeToken<List<InventoryDTO>>() {}.getType()))
//                .thenReturn(inventoryDTOs);
//
//        List<InventoryDTO> result = inventoryService.getAllInventoryItems();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals("Red", result.get(0).getColor());
//        verify(inventoryRepo, times(1)).findAll();
//    }
//
//    @Test
//    public void testGetInventoryItemById() {
//        InventoryItem inventoryItem = new InventoryItem(1, 101, "Red", "M", 10);
//        InventoryDTO inventoryDTO = new InventoryDTO(1, 101, "Red", "M", 10);
//
//        when(inventoryRepo.findById(1)).thenReturn(Optional.of(inventoryItem));
//        when(modelMapper.map(inventoryItem, InventoryDTO.class)).thenReturn(inventoryDTO);
//
//        InventoryDTO result = inventoryService.getInventoryItemById(1);
//
//        assertNotNull(result);
//        assertEquals("Red", result.getColor());
//        verify(inventoryRepo, times(1)).findById(1);
//    }
//
//    @Test
//    public void testSaveInventoryItem() {
//        InventoryDTO inventoryDTO = new InventoryDTO(0, 103, "Green", "S", 30);
//        InventoryItem inventoryItem = new InventoryItem(0, 103, "Green", "S", 30);
//
//        when(modelMapper.map(inventoryDTO, InventoryItem.class)).thenReturn(inventoryItem);
//        when(inventoryRepo.save(inventoryItem)).thenReturn(inventoryItem);
//
//        InventoryDTO result = inventoryService.saveInventoryItem(inventoryDTO);
//
//        assertNotNull(result);
//        assertEquals("Green", result.getColor());
//        verify(inventoryRepo, times(1)).save(inventoryItem);
//    }
//
//    @Test
//    public void testUpdateInventoryItem() {
//        InventoryDTO inventoryDTO = new InventoryDTO(1, 104, "Blue", "M", 50);
//        InventoryItem inventoryItem = new InventoryItem(1, 104, "Blue", "M", 50);
//
//        when(modelMapper.map(inventoryDTO, InventoryItem.class)).thenReturn(inventoryItem);
//        when(inventoryRepo.save(inventoryItem)).thenReturn(inventoryItem);
//
//        InventoryDTO result = inventoryService.updateInventoryItem(inventoryDTO);
//
//        assertNotNull(result);
//        assertEquals("Blue", result.getColor());
//        verify(inventoryRepo, times(1)).save(inventoryItem);
//    }
//
//    @Test
//    public void testDeleteInventoryItem() {
//        Integer inventoryItemId = 1;
//
//        doNothing().when(inventoryRepo).deleteById(inventoryItemId);
//
//        String result = inventoryService.deleteInventoryItem(inventoryItemId);
//
//        assertEquals("Inventory Item deleted successfully", result);
//        verify(inventoryRepo, times(1)).deleteById(inventoryItemId);
//    }
//}
