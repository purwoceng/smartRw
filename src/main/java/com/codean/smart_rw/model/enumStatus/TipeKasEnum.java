package com.codean.smart_rw.model.enumStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipeKasEnum {
    MASUK("Masuk"),
    KELUAR("Keluar");

    @JsonValue
    private final String value;

    @JsonCreator
    public static TipeKasEnum fromValue(String value) {
        for (TipeKasEnum su : values()) {
            if (su.value.equalsIgnoreCase(value) || su.name().equalsIgnoreCase(value)) {
                return su;
            }
        }
        throw new IllegalArgumentException("Invalid Tipe Kas (MASUK atau KELUAR): " + value);
    }
}
