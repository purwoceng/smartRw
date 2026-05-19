package com.codean.smart_rw.service.impl;

import com.codean.smart_rw.mapper.RolesMapper;
import com.codean.smart_rw.model.pojo.RolesPojo;
import com.codean.smart_rw.model.response.*;
import com.codean.smart_rw.service.RolesService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class RolesServiceImpl implements RolesService {
    private final RolesMapper rolesMapper;
    private static final String SUCCESS = "Success";

    private static final Logger log = LogManager.getLogger(RolesServiceImpl.class);

    @Override
    public DatatableResponse<RolesPojo> getDatatable(int page, int limit, String sortField, String sortOrder) {
        try {
            Map<String, String> allowedOrder = new HashMap<>();
            allowedOrder.put("id", "role_id");
            String sortColumn = "role_id";
            if (allowedOrder.containsKey(sortField)) {
                sortColumn = allowedOrder.getOrDefault(sortField, null);
            }
            String sortType = Objects.equals(sortOrder, "DESC") ? "DESC" : "ASC";

            int offset = (page - 1) * limit;
            List<RolesPojo> pageResult = rolesMapper.findAll(offset, limit, sortColumn, sortType);

            PageDataResponse<RolesPojo> data = new PageDataResponse<>(page, limit, pageResult.size(), pageResult);

            return new DatatableResponse<>(
                    SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), data
            );
        } catch (Exception e) {
            log.error("Error when get datatable barang.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<RolesPojo> create(RolesPojo rolesPojo) {
        try {
            rolesPojo.setRoleId(UUID.randomUUID().toString());
            rolesPojo.setNamaRole(rolesPojo.getNamaRole());
            rolesMapper.insert(rolesPojo);

            RolesPojo data = rolesMapper.findById(rolesPojo.getRoleId());
            return new DataResponse<>(SUCCESS, ResponseMessage.DATA_CREATED, HttpStatus.OK.value(), data);
        } catch (Exception e) {
            log.error("Error when create a barang.", e);
            throw e;
        }
    }

    @Override
    public DefaultResponse delete(String id) {
        try {
            rolesMapper.delete(id);
            return new DefaultResponse(SUCCESS, ResponseMessage.DATA_DELETED,HttpStatus.OK.value());
        } catch (Exception e) {
            log.error("Error when delete a barang.", e);
            throw e;
        }
    }

}
