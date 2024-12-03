package com.kaleidoscope.order.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaleidoscope.order.dto.InventoryUpdateDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryUpdateProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryUpdateProducer.class);

    private final NewTopic orderTopic;
    private final KafkaTemplate<String, InventoryUpdateDto> kafkaTemplate;

    public InventoryUpdateProducer(NewTopic orderTopic, KafkaTemplate<String, InventoryUpdateDto> kafkaTemplate) {
        this.orderTopic = orderTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendInventoryUpdateList(List<InventoryUpdateDto> inventoryUpdateDtos) {

        LOGGER.info("Sending InventoryUpdateDto list as JSON: {}", inventoryUpdateDtos);

        try {

            ObjectMapper objectMapper = new ObjectMapper();

            for (InventoryUpdateDto inventoryUpdateDto : inventoryUpdateDtos) {

                String json = objectMapper.writeValueAsString(inventoryUpdateDto);

                Message<String> message = MessageBuilder
                        .withPayload(json)
                        .setHeader(KafkaHeaders.TOPIC, orderTopic.name())
                        .build();

                kafkaTemplate.send(message);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Error serializing InventoryUpdateDto list", e);
        }
    }
}
