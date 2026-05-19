package com.codean.smart_rw.model.pojo;

import com.codean.smart_rw.model.enumStatus.JenisKelaminEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String userId;
    private String nik;
    private String nama;
    private String tempatLahir;
    private LocalDate tanggalLahir;
    private String alamat;
    private String jenisKelamin;
    private String status;
    private String telepon;
    private String chatId;
    private String email;
    private String userName;
    private String password;
    private String umur;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<RolesPojo> roles;
}
