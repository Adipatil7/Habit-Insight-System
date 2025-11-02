package com.data_ingestion_service.DTO;

import java.util.List;
import com.data_ingestion_service.Entity.MachineData;

public class MachineHealthDto {
    private double avgTemperature;
    private double avgVibration;
    private double uptimePercent;
    private String status;

    public MachineHealthDto(List<MachineData> list) {
        if (list == null || list.isEmpty()) {
            this.avgTemperature = 0;
            this.avgVibration = 0;
            this.uptimePercent = 0;
            this.status = "NO_DATA";
            return;
        }

        double tempSum = 0;
        double vibSum = 0;
        int normalCount = 0;
        int warningCount = 0;
        int criticalCount = 0;

        for (MachineData d : list) {
            tempSum += d.getTemperature();
            vibSum += d.getVibration();
            String s = d.getStatus();
            if ("NORMAL".equalsIgnoreCase(s)) normalCount++;
            else if ("WARNING".equalsIgnoreCase(s)) warningCount++;
            else if ("CRITICAL".equalsIgnoreCase(s)) criticalCount++;
        }

        int n = list.size();
        this.avgTemperature = tempSum / n;
        this.avgVibration = vibSum / n;
        this.uptimePercent = ((double) normalCount / n) * 100;

        // Derive overall machine status based on majority
        if (criticalCount > n * 0.3) {
            this.status = "CRITICAL";
        } else if (warningCount > n * 0.3) {
            this.status = "WARNING";
        } else {
            this.status = "NORMAL";
        }
    }

    // Getters
    public double getAvgTemperature() { return avgTemperature; }
    public double getAvgVibration() { return avgVibration; }
    public double getUptimePercent() { return uptimePercent; }
    public String getStatus() { return status; }
}
