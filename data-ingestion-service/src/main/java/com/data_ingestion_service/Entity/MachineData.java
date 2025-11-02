package com.data_ingestion_service.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty("machine_id")
    private String machineId;
    
    private double temperature;
    private double vibration;
    private double pressure;
    private double rpm;

    @JsonProperty("motor_voltage")
    private double motorVoltage;

    @JsonProperty("motor_current")
    private double motorCurrent;

    @JsonProperty("oil_level")
    private double oilLevel;

    @JsonProperty("duty_cycle")
    private double dutyLevel;

    @JsonProperty("timeStamp")
    private LocalDateTime dateTime;

    private String status;

}
