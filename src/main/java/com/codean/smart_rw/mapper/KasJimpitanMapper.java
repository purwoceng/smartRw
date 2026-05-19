package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.KasJimpitanPojo;
import com.codean.smart_rw.model.pojo.TotalSaldoKasPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KasJimpitanMapper {
    List<KasJimpitanPojo> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    KasJimpitanPojo findById(String id);

    TotalSaldoKasPojo findTotalSaldo();

    void insert(KasJimpitanPojo kasJimpitanPojo);

    void update(KasJimpitanPojo kasJimpitanPojo);

    void delete(String id);
}
