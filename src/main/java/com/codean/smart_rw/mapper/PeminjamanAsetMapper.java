package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.DetailPeminjamanAsetPojo;
import com.codean.smart_rw.model.pojo.PeminjamanAsetPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PeminjamanAsetMapper {
    List<PeminjamanAsetPojo> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    PeminjamanAsetPojo findById(String id);

    void insert(PeminjamanAsetPojo peminjamanAsetPojo);

    void insertDetails(DetailPeminjamanAsetPojo detailPeminjamanAsetPojo);

    void update(PeminjamanAsetPojo peminjamanAsetPojo);

    void delete(String id);

    void deleteDetailByPeminjamanId(String id);

    List<DetailPeminjamanAsetPojo> findDetailByPeminjamanId(String peminjamanId);
}
