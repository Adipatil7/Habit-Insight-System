package com.data_ingestion_service.Service.rules;

import org.springframework.stereotype.Component;

import com.data_ingestion_service.Entity.Alert;
import com.data_ingestion_service.Entity.MachineData;
import com.data_ingestion_service.Enums.AlertSeverity;

@Component
public class HighTemperatureRule implements AlertRule {

    @Override
    public boolean matches(MachineData data) {
        return data.getTemperature() > 85.0;
    }

    @Override
    public Alert buildAlert(MachineData data) {
        AlertSeverity severity = (data.getTemperature() > 100.0) ? AlertSeverity.CRITICAL : AlertSeverity.WARNING;
        String message = "High Temperature detected: " + data.getTemperature() + "°C";
        return new Alert(
            data.getMachineId(),
            getAlertType(),
            severity,
            message
        );
    }

    @Override
    public String getAlertType() {
        return "HIGH_TEMPERATURE";
    }
    
}
