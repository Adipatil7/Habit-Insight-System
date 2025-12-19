package com.data_ingestion_service.Service.rules;

import org.springframework.stereotype.Component;

import com.data_ingestion_service.Entity.Alert;
import com.data_ingestion_service.Entity.MachineData;
import com.data_ingestion_service.Enums.AlertSeverity;

@Component
public class HealthDegradationRule implements AlertRule {

    @Override
    public boolean matches(MachineData data) {
        return !"NORMAL".equals(data.getStatus());
    }

    @Override
    public Alert buildAlert(MachineData data) {
        AlertSeverity severity = "CRITICAL".equals(data.getStatus())
        ? AlertSeverity.CRITICAL
        : AlertSeverity.WARNING;
        String message = "Health Degradation detected: " + data.getStatus();
        return new Alert(
                data.getMachineId(),
                getAlertType(),
                severity,
                message);
    }

    @Override
    public String getAlertType() {
        return "HEALTH_DEGRADATION";
    }

}
