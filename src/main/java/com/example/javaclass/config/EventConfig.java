package com.example.javaclass.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Slf4j
public class EventConfig {

    @Value("${topics.name.event}")
    private String eventTopicName;

    @Bean
    public NewTopic eventTopic() {
        log.info("creating topic {}", eventTopicName);
        return TopicBuilder.name(eventTopicName).build();
    }

//    @Bean
//    public AdminClientConfig adminClientConfig() {
//        return new AdminClientConfig();
//    }
}
