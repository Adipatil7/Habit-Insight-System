package com.data_ingestion_service.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    
    
}
