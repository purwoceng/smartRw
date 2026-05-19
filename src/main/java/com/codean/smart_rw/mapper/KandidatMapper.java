package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.KandidatPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KandidatMapper {
    KandidatPojo findById(String id);

    List<KandidatPojo> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    void insert(KandidatPojo kandidatPojo);

    void update(KandidatPojo kandidatPojo);

    void delete(String id);

    void incrementTotalVote(String id);

}
