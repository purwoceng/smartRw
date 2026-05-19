package com.codean.smart_rw.model.enumStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPeminjamanAserEnum {
    DIAJUKAN("Diajukan"),DISETUJUI("Disetujui"),BATAL("Batal");

    @JsonValue
    private final String value;

    @JsonCreator
    public static StatusPeminjamanAserEnum fromValue(String value) {
        for (StatusPeminjamanAserEnum su : values()) {
            if (su.value.equalsIgnoreCase(value) || su.name().equalsIgnoreCase(value)) {
                return su;
            }
        }
        throw new IllegalArgumentException("Invalid Status peminjaman (DIAJUKAN,DISETUJUI,BATAL atau PINDAH): " + value);
    }

}
