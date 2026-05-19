package com.codean.smart_rw.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailPeminjamanAsetPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String peminjamanDetailId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String peminjamanId;

    @NotBlank(message = "item tidak boleh kosong")
    private String asetId;

    @Positive
    private Integer jumlah;
}
