package com.codean.smart_rw.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Getter
@Setter
public class PengumumanPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String pengumumanId;
    private String deskripsi;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String image;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MultipartFile multipartFile;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp updatedAt;
}
