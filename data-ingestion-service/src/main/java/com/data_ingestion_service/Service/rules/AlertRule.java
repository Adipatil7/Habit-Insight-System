package com.data_ingestion_service.Service.rules;

import com.data_ingestion_service.Entity.Alert;
import com.data_ingestion_service.Entity.MachineData;

public interface AlertRule {
    boolean matches(MachineData data);
    Alert buildAlert(MachineData data);
    String getAlertType();
}
