package com.codean.smart_rw.model.pojo;

import com.codean.smart_rw.model.enumStatus.KondisiAsetEnum;
import com.codean.smart_rw.model.enumStatus.StatusAsetEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AsetPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String asetId;
    @NotBlank(message = "kategori aset tidak boleh kosong")
    private String kategoriAset;
    @NotBlank(message = "nama aset tidak boleh kosong")
    private String namaAset;
    @Positive
    private Integer jumlah;
    @Schema(
            description = "Status Aset(TERSEDIA,DIPINJAM,DIPERBAIKI atau RUSAK)",
            example = "TERSEDIA",
            allowableValues = {"TERSEDIA", "DIPINJAM","DIPERBAIKI","RUSAK"}
    )
    private StatusAsetEnum status;
    @Schema(
            description = "Kondisi Aset(BAIK atau RUSAK)",
            example = "BAIK",
            allowableValues = {"BAIK","RUSAK"}
    )
    @NotNull(message = "kondisi aset tidak boleh kosong")
    private KondisiAsetEnum kondisi;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp updatedAt;

}
