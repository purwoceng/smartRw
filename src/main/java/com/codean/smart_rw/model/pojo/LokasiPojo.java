package com.codean.smart_rw.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LokasiPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lokasiId;
    @NotNull(message = "latitude tidak boleh kosong")
    private Double latitude;
    @NotNull(message = "longitude tidak boleh kosong")
    private Double longitude;
    @NotNull(message = "radius tidak boleh kosong")
    private Integer radius;
    @NotBlank(message = "nama lokasi tidak boleh kosong")
    private String namaLokasi;

}
