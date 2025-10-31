package com.data_ingestion_service.Service;

import com.data_ingestion_service.Entity.MachineData;

public interface DataIngestionService {

    MachineData addData(MachineData data);
    
}
