package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.JadwalRondaPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JadwalRondaMapper {
    List<JadwalRondaPojo> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    JadwalRondaPojo findById(String id);

    void insert(JadwalRondaPojo jadwalRondaPojo);

    void update(JadwalRondaPojo jadwalRondaPojo);

    void delete(String id);
}
