package com.codean.smart_rw.service;

import com.codean.smart_rw.model.pojo.AsetPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;

public interface AsetService {
    DatatableResponse<AsetPojo> getDatatable(int page, int limit, String sortField, String sortOrder);

    DataResponse<AsetPojo> findOne(String id);

    DataResponse<AsetPojo> create(AsetPojo asetPojo);

    DataResponse<AsetPojo> update(String id, AsetPojo asetPojo);

    DefaultResponse delete(String id);
}
