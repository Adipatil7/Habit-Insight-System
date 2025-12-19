package com.data_ingestion_service.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data_ingestion_service.Entity.Alert;
import com.data_ingestion_service.Service.AlertService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController()
@RequestMapping("/api/v1/alerts")
public class AlertController {
    
    @Autowired
    private AlertService alertService;

    @GetMapping("/active")
    public List<Alert> getAllActiveAlerts() {
        return alertService.getActiveAlerts();
    }

    @PatchMapping("/acknowledge/{alertId}")
    public Alert acknowledgeAlert(@PathVariable Long alertId) {
        return alertService.acknowledgeAlert(alertId);
    }

    @PatchMapping("/resolve/{alertId}")
    public Alert resolveAlert(@PathVariable Long alertId) {
        return alertService.resolveAlert(alertId);
    }

    @GetMapping("/machine/{machineId}")
    public List<Alert> getAlertsForMachine(@PathVariable String machineId) {
        return alertService.getAlertsForMachine(machineId);
    }

}
