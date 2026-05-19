package com.codean.smart_rw.model.dto;

import com.codean.smart_rw.model.enumStatus.JenisKelaminEnum;
import com.codean.smart_rw.model.enumStatus.StatusUsers;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
public class CreateUsersRequest {
    @NotBlank(message = "nik tidak boleh kosong")
    private String nik;
    @NotBlank(message = "nama tidak boleh kosong")
    private String nama;
    @NotBlank(message = "tempat lahir tidak boleh kosong")
    private String tempatLahir;
    private LocalDate tanggalLahir;
    @NotBlank(message = "alamat tidak boleh kosong")
    private String alamat;
    @Schema(
            description = "Jenis kelamin (LAKI_LAKI atau PEREMPUAN)",
            example = "LAKI_LAKI",
            allowableValues = {"LAKI_LAKI", "PEREMPUAN",}
    )
    private JenisKelaminEnum jenisKelamin;
    @Schema(
            description = "Status User(TETAP,PENDATANG,PINDAH atau PINDAH)",
            example = "TETAP",
            allowableValues = {"TETAP", "PENDATANG","PINDAH","MENINGGAL"}
    )
    private StatusUsers status;
    @Pattern(
            regexp = "^(?:\\+62|62|0)8[1-9][0-9]{6,10}$",
            message = "Invalid Indonesian mobile phone number"
    )
    private String telepon;
    private String chatId;
}
