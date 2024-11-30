package com.kaleidoscope.order.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    private final NewTopic orderTopic;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderProducer(NewTopic orderTopic, KafkaTemplate<String, String> kafkaTemplate) {
        this.orderTopic = orderTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        LOGGER.info(String.format("Sending order event to topic %s", message));

        Message<String> sendingMessage = MessageBuilder
                .withPayload(message)
                .setHeader(KafkaHeaders.TOPIC,orderTopic.name())
                .build();

        kafkaTemplate.send(sendingMessage);
    }
}
