package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.RolesPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolesMapper {
    List<RolesPojo> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    List<RolesPojo> findRolesByUserId(String userId);

    RolesPojo findById(String id);

    void insert(RolesPojo rolesPojo);

    void delete(String id);

}
