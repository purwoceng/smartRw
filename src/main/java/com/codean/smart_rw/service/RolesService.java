package com.codean.smart_rw.service;

import com.codean.smart_rw.model.pojo.RolesPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;

public interface RolesService {
    DatatableResponse<RolesPojo> getDatatable(int page, int limit, String sortField, String sortOrder);
    DataResponse<RolesPojo> create(RolesPojo rolesPojo);
    DefaultResponse delete(String id);
}
