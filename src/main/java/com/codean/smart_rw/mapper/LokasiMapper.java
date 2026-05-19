package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.LokasiPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LokasiMapper {
    List<LokasiPojo> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    LokasiPojo findById(String id);

    void insert(LokasiPojo lokasiPojo);

    void update(LokasiPojo lokasiPojo);

    void delete(String id);
}
