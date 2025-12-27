package com.data_ingestion_service.DTO;

public class MachineReadingEvent implements java.io.Serializable {
    private String machineId;
    private double temperature;
    private double pressure;
    private double vibration;
    private String timestamp;
    private double rpm;
    private double motorVoltage;
    private double motorCurrent;
    private double oilLevel;
    private double dutyCycle;
    private String status;
    public String getMachineId() {
        return machineId;
    }
    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public double getPressure() {
        return pressure;
    }
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
    public double getVibration() {
        return vibration;
    }
    public void setVibration(double vibration) {
        this.vibration = vibration;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public double getRpm() {
        return rpm;
    }
    public void setRpm(double rpm) {
        this.rpm = rpm;
    }
    public double getMotorVoltage() {
        return motorVoltage;
    }
    public void setMotorVoltage(double motorVoltage) {
        this.motorVoltage = motorVoltage;
    }
    public double getMotorCurrent() {
        return motorCurrent;
    }
    public void setMotorCurrent(double motorCurrent) {
        this.motorCurrent = motorCurrent;
    }
    public double getOilLevel() {
        return oilLevel;
    }
    public void setOilLevel(double oilLevel) {
        this.oilLevel = oilLevel;
    }
    public double getDutyCycle() {
        return dutyCycle;
    }
    public void setDutyCycle(double dutyCycle) {
        this.dutyCycle = dutyCycle;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public MachineReadingEvent() {
    }
    public MachineReadingEvent(String machineId, double temperature, double pressure, double vibration,
            String timestamp, double rpm, double motorVoltage, double motorCurrent, double oilLevel, double dutyCycle,
            String status) {
        this.machineId = machineId;
        this.temperature = temperature;
        this.pressure = pressure;
        this.vibration = vibration;
        this.timestamp = timestamp;
        this.rpm = rpm;
        this.motorVoltage = motorVoltage;
        this.motorCurrent = motorCurrent;
        this.oilLevel = oilLevel;
        this.dutyCycle = dutyCycle;
        this.status = status;
    }
    
    @Override
public String toString() {
    return "MachineReadingEvent{" +
            "machineId='" + machineId + '\'' +
            ", temperature=" + temperature +
            ", pressure=" + pressure +
            ", vibration=" + vibration +
            ", rpm=" + rpm +
            ", status='" + status + '\'' +
            '}';
}

    
}
