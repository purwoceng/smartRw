package com.codean.smart_rw.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class TimeVotePojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String timeVoteId;
    @NotNull(message = "Jam Mulai tidak boleh kosong")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @Schema(
            description = "Waktu mulai pilihan",
            example = "2026-01-05 18:00:00",
            type = "string",
            format = "date-time",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime jamMulai;
    @NotNull(message = "jam selesai tidak boleh kosong")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @Schema(
            description = "Waktu selesai pilihan",
            example = "2026-01-05 22:00:00",
            type = "string",
            format = "date-time",
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private LocalDateTime jamSelesai;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Jakarta")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp updatedAt;
}
