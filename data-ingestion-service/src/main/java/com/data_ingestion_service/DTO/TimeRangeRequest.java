// src/main/java/.../DTO/TimeRangeRequest.java
package com.data_ingestion_service.DTO;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimeRangeRequest {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private OffsetDateTime t1;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private OffsetDateTime t2;

    public OffsetDateTime getT1() {
        return t1;
    }

    public void setT1(OffsetDateTime t1) {
        this.t1 = t1;
    }

    public OffsetDateTime getT2() {
        return t2;
    }

    public void setT2(OffsetDateTime t2) {
        this.t2 = t2;
    }
}
