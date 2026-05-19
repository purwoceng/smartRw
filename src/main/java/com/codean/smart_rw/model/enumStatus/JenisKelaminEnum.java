package com.codean.smart_rw.model.enumStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JenisKelaminEnum {
    LAKI_LAKI("Laki-laki"),
    PEREMPUAN("Perempuan");

    @JsonValue
    private final String value;

    @JsonCreator
    public static JenisKelaminEnum fromValue(String value) {
        for (JenisKelaminEnum jk : values()) {
            if (jk.value.equalsIgnoreCase(value) || jk.name().equalsIgnoreCase(value)) {
                return jk;
            }
        }
        throw new IllegalArgumentException("Invalid jenis kelamin (LAKI_LAKI atau PEREMPUAN): " + value);
    }
}
