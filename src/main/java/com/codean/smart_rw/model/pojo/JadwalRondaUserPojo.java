package com.codean.smart_rw.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class JadwalRondaUserPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String jadwalWargaId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String jadwalId;
    private String userId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String nama;

    @JsonIgnore
    private String hari;
    @JsonIgnore
    private LocalTime jamMulai;
    @JsonIgnore
    private String namaLokasi;
}
