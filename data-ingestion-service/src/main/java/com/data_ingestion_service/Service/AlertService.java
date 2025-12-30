package com.data_ingestion_service.Service;

import java.util.List;

import com.data_ingestion_service.DTO.MachineReadingEvent;
import com.data_ingestion_service.Entity.Alert;
public interface AlertService {

    void evaluateAndGenerateAlerts(MachineReadingEvent event);

    List<Alert> getActiveAlerts();

    Alert acknowledgeAlert(Long alertId);

    Alert resolveAlert(Long alertId);

    List<Alert> getAlertsForMachine(String machineId);

}
