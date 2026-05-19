package com.codean.smart_rw.model.enumStatus;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KondisiAsetEnum {
    BAIK("Baik"),
    RUSAK("Rusak");

    @JsonValue
    private final String value;

    @JsonCreator
    public static KondisiAsetEnum fromValue(String value) {
        for (KondisiAsetEnum jk : values()) {
            if (jk.value.equalsIgnoreCase(value) || jk.name().equalsIgnoreCase(value)) {
                return jk;
            }
        }
        throw new IllegalArgumentException("Invalid Kondisi aset (BAIK atau RUSAK): " + value);
    }
}
