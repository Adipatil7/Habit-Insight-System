package com.data_ingestion_service.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MachineData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String machineId;
    
    private double temperature;
    private double vibration;
    private double pressure;
    private double rpm;

    private double motorVoltage;

    private double motorCurrent;

    private double oilLevel;

    private double dutyLevel;

    private LocalDateTime dateTime;

    private String status;

    private double healthScore;

    private boolean failureFlag;

}
