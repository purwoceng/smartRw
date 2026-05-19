package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.AsetPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AsetMapper {
    List<AsetPojo> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    AsetPojo findById(String id);

    void insert(AsetPojo asetPojo);

    void update(AsetPojo asetPojo);

    void delete(String id);
}
