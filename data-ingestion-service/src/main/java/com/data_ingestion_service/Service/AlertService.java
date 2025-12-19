package com.data_ingestion_service.Service;

import java.util.List;

import com.data_ingestion_service.Entity.Alert;
import com.data_ingestion_service.Entity.MachineData;

public interface AlertService {

    void evaluateAndGenerateAlerts(MachineData data);

    List<Alert> getActiveAlerts();

    Alert acknowledgeAlert(Long alertId);

    Alert resolveAlert(Long alertId);

    List<Alert> getAlertsForMachine(String machineId);

}
