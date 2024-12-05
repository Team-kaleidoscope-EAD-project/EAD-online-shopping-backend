package com.kaleidoscope.inventory.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaleidoscope.inventory.dto.InventoryDTO;
import com.kaleidoscope.inventory.dto.InventoryUpdateDTO;
import com.kaleidoscope.inventory.dto.UpdateDto;
import com.kaleidoscope.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @Autowired
    private InventoryService inventoryService;

    @KafkaListener(
            topics = "${spring.kafka.template.default-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(String message) {
        LOGGER.info("Receiving the order event from the topic: {}", message);

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            InventoryUpdateDTO inventoryUpdate = objectMapper.readValue(message, InventoryUpdateDTO.class);

            UpdateDto updateDto = new UpdateDto(inventoryUpdate.getSku(), inventoryUpdate.getQuantity());
            LOGGER.info("Created UpdateDto: {}", updateDto);

            try {
                InventoryDTO updatedInventory = inventoryService.updateInventory(updateDto);
                LOGGER.info("Updated InventoryDTO: {}", updatedInventory);
            } catch (IllegalArgumentException e) {
                LOGGER.error("Failed to update inventory: {}", e.getMessage());
            }

        } catch (JsonProcessingException e) {
            LOGGER.error("Error deserializing the message: {}", message, e);
        }
    }
}
