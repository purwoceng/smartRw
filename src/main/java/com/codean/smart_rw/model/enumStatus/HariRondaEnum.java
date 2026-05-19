package com.codean.smart_rw.model.enumStatus;

import java.time.DayOfWeek;

public enum HariRondaEnum {
    SENIN,
    SELASA,
    RABU,
    KAMIS,
    JUMAT,
    SABTU,
    MINGGU;

    public static HariRondaEnum fromDayOfWeek(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> SENIN;
            case TUESDAY -> SELASA;
            case WEDNESDAY -> RABU;
            case THURSDAY -> KAMIS;
            case FRIDAY -> JUMAT;
            case SATURDAY -> SABTU;
            case SUNDAY -> MINGGU;
        };
    }
}
