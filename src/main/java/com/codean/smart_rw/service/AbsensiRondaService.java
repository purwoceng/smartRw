package com.codean.smart_rw.service;

import com.codean.smart_rw.model.pojo.AbsensiRondaPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;

public interface AbsensiRondaService {
    DatatableResponse<AbsensiRondaPojo> getDatatable(int page, int limit, String sortField, String sortOrder);

    DataResponse<AbsensiRondaPojo> findOne(String id);

    DataResponse<AbsensiRondaPojo> create(AbsensiRondaPojo absensiRondaPojo);

    DataResponse<AbsensiRondaPojo> update(String id, AbsensiRondaPojo absensiRondaPojo);

    DefaultResponse delete(String id);
}
