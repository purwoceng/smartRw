package com.codean.smart_rw.model.pojo;

import com.codean.smart_rw.model.enumStatus.StatusAbsensiRondaEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AbsensiRondaPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String absensiId;
    @NotBlank(message = "jadwal tidak boleh kosong")
    private String jadwalWargaId;
    @NotNull(message = "status tidak boleh kosong")
    @Schema(
            description = "Status Absensi Ronda(HADIR,IZIN atau TIDAK_HADIR)",
            example = "HADIR",
            allowableValues = {"HADIR", "IZIN","TIDAK_HADIR"}
    )
    private StatusAbsensiRondaEnum status;
    private String keterangan;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalTime jamMasuk;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalTime jamKeluar;
    private Double latitude;
    private Double longitude;
    private Integer jarak;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer radius;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp updatedAt;

}
