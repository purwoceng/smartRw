package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.AbsensiRondaPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AbsensiRondaMapper {
    List<AbsensiRondaPojo> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    AbsensiRondaPojo findById(String id);

    void insert(AbsensiRondaPojo absensiRondaPojo);

    void update(AbsensiRondaPojo absensiRondaPojo);

    void delete(String id);

    boolean cekAbsen(String jadwalId);
}
