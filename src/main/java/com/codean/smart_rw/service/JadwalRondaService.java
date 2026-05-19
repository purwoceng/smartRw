package com.codean.smart_rw.service;

import com.codean.smart_rw.model.pojo.JadwalRondaPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;

public interface JadwalRondaService {
    DatatableResponse<JadwalRondaPojo> getDatatable(int page, int limit, String sortField, String sortOrder);

    DataResponse<JadwalRondaPojo> findOne(String id);

    DataResponse<JadwalRondaPojo> create(JadwalRondaPojo jadwalRondaPojo);

    DataResponse<JadwalRondaPojo> update(String id, JadwalRondaPojo jadwalRondaPojo);

    DefaultResponse delete(String id);
}
