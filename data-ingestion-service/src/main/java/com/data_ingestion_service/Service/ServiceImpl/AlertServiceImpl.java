package com.data_ingestion_service.Service.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.data_ingestion_service.DTO.MachineReadingEvent;
import com.data_ingestion_service.Entity.Alert;
import com.data_ingestion_service.Entity.MachineData;
import com.data_ingestion_service.Enums.AlertStatus;
import com.data_ingestion_service.Repository.AlertRepo;
import com.data_ingestion_service.Service.AlertService;
import com.data_ingestion_service.Service.rules.AlertRule;

@Service
public class AlertServiceImpl implements AlertService {

    private final List<AlertRule> alertRules;
    private final AlertRepo alertRepo;

    @Autowired
    private MachineDataConversionImpl dataConverter;

    @Autowired
    public AlertServiceImpl(List<AlertRule> alertRules, AlertRepo alertRepo) {
        this.alertRules = alertRules;
        this.alertRepo = alertRepo;
    }

    @Override
    public void evaluateAndGenerateAlerts(MachineReadingEvent event) {

        MachineData data = dataConverter.convertData(event);

        for (AlertRule rule : alertRules) {
            boolean matched = rule.matches(data);
            alertRepo.findByMachineIdAndAlertTypeAndStatus(data.getMachineId(), rule.getAlertType(), AlertStatus.ACTIVE)
                    .ifPresentOrElse(existingAlert -> {
                        if (!matched) {
                            existingAlert.setStatus(AlertStatus.RESOLVED);
                            existingAlert.setResolvedAt(data.getDateTime());
                            alertRepo.save(existingAlert);
                            System.out.println("Resolved Alert: " + existingAlert);
                        }
                    }, () -> {
                        if (matched) {
                            Alert alert = rule.buildAlert(data);
                            System.out.println("Generated Alert: " + alert);
                            alertRepo.save(alert);
                        }
                    });
        } 

    }

    @Override
    public List<Alert> getActiveAlerts() {
        return alertRepo.findByStatusOrderByCreatedAtDesc(AlertStatus.ACTIVE);
    }

    @Override
public Alert acknowledgeAlert(Long alertId) {

    Alert alert = alertRepo.findById(alertId)
        .orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Alert not found with id: " + alertId
            )
        );

    if (alert.getStatus() != AlertStatus.ACTIVE) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Only ACTIVE alerts can be acknowledged"
        );
    }

    alert.setStatus(AlertStatus.ACKNOWLEDGED);
    alert.setAcknowledgedAt(LocalDateTime.now());

    return alertRepo.save(alert);
}


    @Override
public Alert resolveAlert(Long alertId) {

    Alert alert = alertRepo.findById(alertId)
        .orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Alert not found with id: " + alertId
            )
        );

    if (alert.getStatus() == AlertStatus.RESOLVED) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Alert is already RESOLVED."
        );
    }

    alert.setStatus(AlertStatus.RESOLVED);
    alert.setResolvedAt(LocalDateTime.now());

    return alertRepo.save(alert);
}


    @Override
    public List<Alert> getAlertsForMachine(String machineId) {
        return alertRepo.findByMachineIdOrderByCreatedAtDesc(machineId);
    }

}
