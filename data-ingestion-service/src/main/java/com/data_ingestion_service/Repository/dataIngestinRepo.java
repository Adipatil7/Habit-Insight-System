package com.data_ingestion_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data_ingestion_service.Entity.MachineData;

public interface dataIngestinRepo extends JpaRepository<MachineData , Integer> {

}
