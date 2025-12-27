package com.data_ingestion_service.config;

import com.data_ingestion_service.DTO.MachineReadingEvent;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfig {
    
    @Bean
    public ConsumerFactory<String, MachineReadingEvent> ConsumerFactory(){
        
        Map<String , Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"ingestion-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonDeserializer.class);

        JsonDeserializer<MachineReadingEvent> jsonDeserializer = new JsonDeserializer<>(MachineReadingEvent.class);
        jsonDeserializer.addTrustedPackages("*");
        
        return new DefaultKafkaConsumerFactory<>(props,
            new StringDeserializer(),
            jsonDeserializer
        );
        
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MachineReadingEvent>kafkaListenerContainerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, MachineReadingEvent>factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ConsumerFactory());
        return factory;
    }
    

}
