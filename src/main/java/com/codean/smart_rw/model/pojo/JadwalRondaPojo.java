package com.codean.smart_rw.model.pojo;

import com.codean.smart_rw.model.enumStatus.HariRondaEnum;
import com.codean.smart_rw.model.enumStatus.StatusJadwalEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class JadwalRondaPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String jadwalId;
    @NotNull(message = "Hari Ronda tidak boleh kosong")
    private HariRondaEnum hari;
    @NotNull(message = "Jam mulai ronda tidak boleh kosong")
    @Schema(
            type = "string",
            example = "19:30",
            description = "Format jam HH:mm"
    )
    @JsonFormat(pattern = "HH:mm")
    private LocalTime jamMulai;
    @NotNull(message = "Jam selesai ronda tidak boleh kosong")
    @Schema(
            type = "string",
            example = "23:30",
            description = "Format jam HH:mm"
    )
    @JsonFormat(pattern = "HH:mm")
    private LocalTime jamSelesai;
    private StatusJadwalEnum status;
    @NotBlank(message = "lokasi tidak boleh kosong")
    private String lokasiId;

    private List<JadwalRondaUserPojo> userId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp updatedAt;
}
