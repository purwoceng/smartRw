package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.PengumumanPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PengumumanMapper {
    List<PengumumanPojo> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    PengumumanPojo findById(String id);

    void insert(PengumumanPojo pengumumanPojo);

    void delete(String id);
}
