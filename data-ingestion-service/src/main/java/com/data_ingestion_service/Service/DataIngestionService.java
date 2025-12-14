package com.data_ingestion_service.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.data_ingestion_service.DTO.MachineHealthDto;
import com.data_ingestion_service.Entity.MachineData;

public interface DataIngestionService {

    MachineData addData(MachineData data);

    List<MachineData> getData(String machineId);

    MachineData getLatestData(String machineId);

    List<MachineData> getRecentData(String machineId);

    List<MachineData> getCustomData(String machineId , LocalDateTime t1 , LocalDateTime t2);

    int delete(String machineId);

    MachineHealthDto getHealthForMachine(String machineId , int hours);
    
}
