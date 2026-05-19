package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.LogNotifikasiPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogNotifikasiMapper {
    List<LogNotifikasiPojo> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    LogNotifikasiPojo findById(String id);

    void insert(LogNotifikasiPojo logNotifikasiPojo);
}
