package com.codean.smart_rw.model.enumStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusUsers {
    TETAP("Tetap"),
    PENDATANG("Pendatang"),
    MENINGGAL("Meninggal"),
    PINDAH("PINDAH");

    @JsonValue
    private final String value;

    @JsonCreator
    public static StatusUsers fromValue(String value) {
        for (StatusUsers su : values()) {
            if (su.value.equalsIgnoreCase(value) || su.name().equalsIgnoreCase(value)) {
                return su;
            }
        }
        throw new IllegalArgumentException("Invalid Status users (TETAP,PENDATANG,PINDAH atau PINDAH): " + value);
    }
}
