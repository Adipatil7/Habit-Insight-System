package com.data_ingestion_service.Service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data_ingestion_service.DTO.MachineReadingEvent;
import com.data_ingestion_service.Entity.MachineData;
import com.data_ingestion_service.Repository.dataIngestinRepo;

@Service
public class MachineDataConversionImpl {

    @Autowired
    private dataIngestinRepo repo;

    public MachineData convertData(MachineReadingEvent event) {
        MachineData data = new MachineData();

        data.setMachineId(event.getMachineId());
        data.setTemperature(event.getTemperature());
        data.setVibration(event.getVibration());
        data.setPressure(event.getPressure());
        data.setRpm(event.getRpm());
        data.setMotorVoltage(event.getMotorVoltage());
        data.setMotorCurrent(event.getMotorCurrent());
        data.setOilLevel(event.getOilLevel());
        data.setDutyLevel(event.getDutyCycle());
        data.setStatus(event.getStatus());

        data.setDateTime(
                java.time.LocalDateTime.parse(event.getTimestamp()));

        double healthScore = computeHealthScore(
                event.getTemperature(),
                event.getVibration(),
                event.getPressure());

        data.setHealthScore(healthScore);
        boolean failureFlag = determineFailure(event);
        data.setFailureFlag(failureFlag);

        return data;

    }

    private double computeHealthScore(
            double temperature,
            double vibration,
            double pressure) {
        double tempSeverity;
        if (temperature <= 85) {
            tempSeverity = 0;
        } else if (temperature >= 110) {
            tempSeverity = 1;
        } else {
            tempSeverity = (temperature - 85) / (110 - 85);
        }

        double vibSeverity;
        if (vibration <= 4) {
            vibSeverity = 0;
        } else if (vibration >= 10) {
            vibSeverity = 1;
        } else {
            vibSeverity = (vibration - 4) / (10 - 4);
        }

        double pressSeverity;
        if (pressure >= 8.5) {
            pressSeverity = 0;
        } else if (pressure <= 7) {
            pressSeverity = 1;
        } else {
            pressSeverity = (8.5 - pressure) / (8.5 - 7);
        }

        double severity = 0.45 * tempSeverity +
                0.35 * vibSeverity +
                0.20 * pressSeverity;

        double healthScore = 100 * (1 - severity);

        return Math.max(0, Math.min(100, healthScore));
    }

    private boolean determineFailure(MachineReadingEvent event) {

        if (!"CRITICAL".equals(event.getStatus())) {
            return false;
        }

        List<MachineData> lastReadings = repo.findTop2ByMachineIdOrderByDateTimeDesc(event.getMachineId());

        if (lastReadings.size() < 2) {
            return false;
        }

        return "CRITICAL".equals(lastReadings.get(0).getStatus()) &&
                "CRITICAL".equals(lastReadings.get(1).getStatus());
    }

}
