package com.data_ingestion_service.Controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.data_ingestion_service.DTO.MachineHealthDto;
import com.data_ingestion_service.DTO.TimeRangeRequest;
import com.data_ingestion_service.Entity.MachineData;
import com.data_ingestion_service.Service.AlertService;
import com.data_ingestion_service.Service.DataIngestionService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController()
@RequestMapping("api/v1")
public class MainController {

    // post api for simulator to send data to this microservice
    @Autowired
    private DataIngestionService dataIngestionservice;

    @Autowired
    private AlertService alertService;

    @PostMapping("/sendData")
    public ResponseEntity<String> getData(@RequestBody MachineData data) {

        this.dataIngestionservice.addData(data);
        this.alertService.evaluateAndGenerateAlerts(data);
        return ResponseEntity.ok("data recieved successfully !!");

    }

    @GetMapping("/latest/{machineId}")
    public ResponseEntity<MachineData> fetchData(@PathVariable String machineId) {
        return ResponseEntity.ok(this.dataIngestionservice.getLatestData(machineId));
    }

    @GetMapping("/recent/{machineId}")
    public ResponseEntity<List<MachineData>> fetchRecentData(@PathVariable String machineId) {
        return ResponseEntity.ok(this.dataIngestionservice.getRecentData(machineId));
    }

 @PostMapping("/custom/{machineId}")
public ResponseEntity<List<MachineData>> fetchCustomData(
        @PathVariable String machineId,
        @RequestBody TimeRangeRequest request) {

    ZonedDateTime z1 = request.getT1().atZoneSameInstant(ZoneId.systemDefault());
    ZonedDateTime z2 = request.getT2().atZoneSameInstant(ZoneId.systemDefault());

    LocalDateTime from = z1.toLocalDateTime();
    LocalDateTime to   = z2.toLocalDateTime();

    System.out.println("Converted time range (LOCAL): " + from + " -> " + to);

    List<MachineData> dataList =
        dataIngestionservice.getCustomData(machineId, from, to);

    System.out.println("Fetched " + dataList.size() + " records for machineId: " + machineId);

    return ResponseEntity.ok(dataList);
}


    @DeleteMapping("/delete/{machineId}")
    public ResponseEntity<String> deleteAll(@PathVariable String machineId) {
        int count = this.dataIngestionservice.delete(machineId);
        return ResponseEntity.ok("Deleted :" + " " + count + " " + "rows successfully !!");
    }

    @GetMapping("/health/{machineId}")
    public ResponseEntity<MachineHealthDto> getHealth(@PathVariable String machineId,
            @RequestParam(defaultValue = "1") int hours) {

        MachineHealthDto dto = this.dataIngestionservice.getHealthForMachine(machineId, hours);

        return ResponseEntity.ok(dto);

    }

}
