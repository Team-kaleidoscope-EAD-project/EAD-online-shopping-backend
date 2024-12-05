package com.kaleidoscope.order.kafka;

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

@Service
public class InventoryUpdateProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryUpdateProducer.class);

    private final NewTopic orderTopic;
    private final KafkaTemplate<String, InventoryUpdateDto> kafkaTemplate;

    public InventoryUpdateProducer(NewTopic orderTopic, KafkaTemplate<String, InventoryUpdateDto> kafkaTemplate) {
        this.orderTopic = orderTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendInventoryUpdate(InventoryUpdateDto inventoryUpdateDto) {
        LOGGER.info(String.format("Sending InventoryUpdateDto event to topic: %s", orderTopic.name()));

        Message<InventoryUpdateDto> message = MessageBuilder
                .withPayload(inventoryUpdateDto)
                .setHeader(KafkaHeaders.TOPIC, orderTopic.name())
                .build();

        kafkaTemplate.send(message);
    }
}
