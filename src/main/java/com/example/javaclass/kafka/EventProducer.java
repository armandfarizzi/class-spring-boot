package com.example.javaclass.kafka;


import com.example.javaclass.dto.EventDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    private NewTopic topic;
    private KafkaTemplate<String, EventDto> kafkaTemplate;

    public EventProducer(NewTopic topic, KafkaTemplate<String, EventDto> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(EventDto event) {
        Message<EventDto> message = MessageBuilder.withPayload(event).setHeader(KafkaHeaders.TOPIC, topic.name()).build();
        kafkaTemplate.send(message);
    }
}
