package com.codean.smart_rw.service;

import com.codean.smart_rw.model.dto.ActivationUsersRequest;
import com.codean.smart_rw.model.dto.CreateUsersRequest;
import com.codean.smart_rw.model.dto.LoginUsersDTO;
import com.codean.smart_rw.model.pojo.UsersPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;
import com.codean.smart_rw.model.response.LoginResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface UsersService {
    DatatableResponse<UsersPojo> getDatatable(int page, int limit, String sortField, String sortOrder);

    DataResponse<UsersPojo> createUsers(CreateUsersRequest createUsersRequest);

    DataResponse<UsersPojo> activationUsers(String id,ActivationUsersRequest activationUsersRequest);

    DefaultResponse delete(String id);

    DataResponse<LoginResponse> login(LoginUsersDTO loginUsersDTO);

    DataResponse<UsersPojo> findOne (String id);

    DataResponse<UsersPojo> updateUsers(String id,UsersPojo usersPojo);

    //void generateExcel(HttpServletResponse response) throws IOException;
}
