package com.data_ingestion_service.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data_ingestion_service.Entity.MachineData;

public interface dataIngestinRepo extends JpaRepository<MachineData , Integer> {

    List<MachineData> findByMachineIdAndDateTimeAfterOrderByDateTimeDesc(String machineId, LocalDateTime after);

    List<MachineData> findByMachineIdAndDateTimeBetweenOrderByDateTimeDesc(
        String machineId,
        LocalDateTime startTime,
        LocalDateTime endTime
    );

    int deleteAllByMachineId(String machineId);

    Optional<MachineData>
    findTopByMachineIdOrderByDateTimeDesc(String machineId);

}
