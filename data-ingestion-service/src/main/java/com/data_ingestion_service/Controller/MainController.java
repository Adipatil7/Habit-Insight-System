package com.data_ingestion_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data_ingestion_service.Entity.MachineData;
import com.data_ingestion_service.Service.DataIngestionService;

@RestController()
@RequestMapping("api/v1")
public class MainController {
    
    //post api for simulator to send data to this microservice
    @Autowired
    private DataIngestionService dataIngestionservice;

    @PostMapping("/sendData")
    public ResponseEntity<String> getData(@RequestBody MachineData data){

        this.dataIngestionservice.addData(data);
        
        return ResponseEntity.ok("data recieved successfully !!");

    }

}
