package com.codean.smart_rw.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class LogNotifikasiPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String logId;
    private String pengumumanId;
    private String userId;
    private String channel;
    private String status;
    private String responseTelegram;
    private String jenisNotifikasi;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp sentAt;
}
