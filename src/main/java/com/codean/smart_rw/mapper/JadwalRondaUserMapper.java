package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.AbsensiRondaPojo;
import com.codean.smart_rw.model.pojo.JadwalRondaUserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalTime;
import java.util.List;

@Mapper
public interface JadwalRondaUserMapper {
    void insert(JadwalRondaUserPojo jadwalRondaUserPojo);

    void update(JadwalRondaUserPojo jadwalRondaUserPojo);

    void deleteByJadwalId(String jadwalId);

    List<JadwalRondaUserPojo> findByJadwalId(String jadwalId);

    List<JadwalRondaUserPojo> findByDays(@Param("hari") String hari, @Param("jam") LocalTime jam);

    AbsensiRondaPojo findJadwalUser(String userId, String hari);


}
