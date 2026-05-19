package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.UserRoles;
import com.codean.smart_rw.model.pojo.UsersPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UsersMapper {
    UsersPojo findById(String id);

    List<UsersPojo> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    void insertUsers(UsersPojo usersPojo);

    void activationUsers(UsersPojo usersPojo);

    void insertUserRole(UserRoles userRoles);

    void updateUsers(UsersPojo usersPojo);

    void delete(String id);

    Optional<UsersPojo> findByNik(String nik);

    int totalData();

    Optional<UsersPojo> findByEmail(String email);

    List<UsersPojo> findUserWithChatId();
}
