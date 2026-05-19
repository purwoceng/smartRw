package com.codean.smart_rw.model.enumStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusAbsensiRondaEnum {
    HADIR("Hadir"),
    TIDAK_HADIR("Tidak Hadir"),
    IZIN("Izin");

    @JsonValue
    private final String value;

    @JsonCreator
    public static StatusAbsensiRondaEnum fromValue(String value) {
        for (StatusAbsensiRondaEnum jk : values()) {
            if (jk.value.equalsIgnoreCase(value) || jk.name().equalsIgnoreCase(value)) {
                return jk;
            }
        }
        throw new IllegalArgumentException("Invalid Status Absensi Ronda (HADIR,IZIN atau TIDAK_HADIR): " + value);
    }
}
