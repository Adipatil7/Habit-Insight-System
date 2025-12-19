package com.data_ingestion_service.Service.rules;

import org.springframework.stereotype.Component;

import com.data_ingestion_service.Entity.Alert;
import com.data_ingestion_service.Entity.MachineData;
import com.data_ingestion_service.Enums.AlertSeverity;

@Component
public class HighVibrationRule implements AlertRule {

    @Override
    public boolean matches(MachineData data) {
        return data.getVibration() > 4.0;
    }

    @Override
    public Alert buildAlert(MachineData data) {
        AlertSeverity severity = (data.getVibration() > 8.0) ? AlertSeverity.CRITICAL : AlertSeverity.WARNING;
        String message = "High Vibration detected: " + data.getVibration() + " mm/s";
        return new Alert(
            data.getMachineId(),
            getAlertType(),
            severity,
            message
        );
    }

    @Override
    public String getAlertType() {
        return "HIGH_VIBRATION";
    }
    
}
