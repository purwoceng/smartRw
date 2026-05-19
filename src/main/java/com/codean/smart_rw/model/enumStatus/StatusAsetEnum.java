package com.codean.smart_rw.model.enumStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusAsetEnum {
    TERSEDIA("Tersedia"),
    DIPINJAM("Dipinjam"),
    DIPERBAIKI("Diperbaiki"),
    RUSAK("Rusak");

    @JsonValue
    private final String value;

    @JsonCreator
    public static StatusAsetEnum fromValue(String value) {
        for (StatusAsetEnum su : values()) {
            if (su.value.equalsIgnoreCase(value) || su.name().equalsIgnoreCase(value)) {
                return su;
            }
        }
        throw new IllegalArgumentException("Invalid Status aset (TERSEDIA,DIPINJAM,DIPERBAIKI atau RUSAK): " + value);
    }
}
