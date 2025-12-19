package com.data_ingestion_service.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data_ingestion_service.Entity.Alert;
import com.data_ingestion_service.Enums.AlertStatus;

public interface AlertRepo extends JpaRepository<Alert, Long> {
   
    Optional<Alert> findByMachineIdAndAlertTypeAndStatus(String machineId, String alertType, AlertStatus status);

    List<Alert> findByMachineIdOrderByCreatedAtDesc(String machineId);
    
    List<Alert> findByStatusOrderByCreatedAtDesc(AlertStatus status);

}
