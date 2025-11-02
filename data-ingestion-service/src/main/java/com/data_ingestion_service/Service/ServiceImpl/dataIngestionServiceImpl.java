package com.data_ingestion_service.Service.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.data_ingestion_service.DTO.MachineHealthDto;
import com.data_ingestion_service.Entity.MachineData;
import com.data_ingestion_service.Repository.dataIngestinRepo;
import com.data_ingestion_service.Service.DataIngestionService;

@Service
public class dataIngestionServiceImpl implements DataIngestionService{

    @Autowired
    private dataIngestinRepo repo;

    @SuppressWarnings("null")
    @Override
    public MachineData addData(MachineData data) {
        this.repo.save(data);
        return data;
    }

    @Override
    public List<MachineData> getData(String machineId) {
        LocalDateTime thirtySecondsAgo = LocalDateTime.now().minusSeconds(30);
        List<MachineData> data = repo.findByMachineIdAndDateTimeAfterOrderByDateTimeDesc(machineId, thirtySecondsAgo);
        return data;
    }

    @Override
    public List<MachineData> getRecentData(String machineId) {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusSeconds(300);
        List<MachineData> data = repo.findByMachineIdAndDateTimeAfterOrderByDateTimeDesc(machineId, fiveMinutesAgo);
        return data;
    } 

    @Override
    public List<MachineData> getCustomData(String machineId , LocalDateTime t1 , LocalDateTime t2) { 
        List<MachineData> data = repo.findByMachineIdAndDateTimeBetweenOrderByDateTimeDesc(machineId, t1,t2);
        return data;
    }

    @Override
    @Transactional
    public int delete(String machineId) {
        return this.repo.deleteAllByMachineId(machineId);
    }

    @Override
    public MachineHealthDto getHealthForMachine(String machineId , int hours) {
        LocalDateTime start = LocalDateTime.now().minusHours(hours);
        LocalDateTime end = LocalDateTime.now();

        List<MachineData> readings = repo.findByMachineIdAndDateTimeBetweenOrderByDateTimeDesc(machineId, start, end);
        if (readings.isEmpty()) return null;

        return new MachineHealthDto(readings);
    } 

    
    
}
