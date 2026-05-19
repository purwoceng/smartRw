package com.codean.smart_rw.model.pojo;

import com.codean.smart_rw.model.enumStatus.TipeKasEnum;
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
public class KasJimpitanPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String kasId;
    @Schema(
            description = "Tipe Kas(MASUK atau KELUAR)",
            example = "MASUK",
            allowableValues = {"MASUK","KELUAR"}
    )
    @NotNull(message = "tipe kas tidak boleh kosong")
    private TipeKasEnum tipe;
    @Positive
    private Double jumlah;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double saldo;
    @NotBlank(message = "keterangan tidak boleh kosong")
    private String keterangan;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String userId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp updatedAt;
}
