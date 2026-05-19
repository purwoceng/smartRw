package com.codean.smart_rw.model.pojo;

import com.codean.smart_rw.model.enumStatus.StatusPeminjamanAserEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PeminjamanAsetPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String peminjamanId;
    @Schema(
            description = "Status Aset(DIAJUKAN,DISETUJUI,DIBATALKAN)",
            example = "DIAJUKAN",
            allowableValues = {"DIAJUKAN", "DISETUJUI","DIBATALKAN"
            }
    )
    @NotNull(message = "status tidak boleh kosong")
    private StatusPeminjamanAserEnum status;
    private String keterangan;
    @NotNull(message = "Jam Mulai tidak boleh kosong")
    private LocalDateTime tanggalPinjam;
    @NotNull(message = "jam selesai tidak boleh kosong")
    private LocalDateTime tanggalKembali;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Jakarta")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp updatedAt;

    private List<DetailPeminjamanAsetPojo> items;
}
