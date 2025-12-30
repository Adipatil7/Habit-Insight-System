package com.data_ingestion_service.Kafka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.data_ingestion_service.DTO.MachineReadingEvent;
import com.data_ingestion_service.Service.AlertService;
import com.data_ingestion_service.Service.DataIngestionService;

@Component
public class MachineReadingConsumer {

    @Autowired
    private DataIngestionService dataIngestionService;
    
    @Autowired
    private AlertService alertService;

    @KafkaListener(
        topics = "mecha-data",
        groupId = "ingestion-group"
    )
    public void consumer(MachineReadingEvent event){
        System.out.println("Consumed event: " + event);
        this.dataIngestionService.saveFromKafka(event);
        this.alertService.evaluateAndGenerateAlerts(event);
    }

}
