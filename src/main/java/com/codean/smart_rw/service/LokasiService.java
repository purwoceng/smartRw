package com.codean.smart_rw.service;

import com.codean.smart_rw.model.pojo.LokasiPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;

public interface LokasiService {
    DatatableResponse<LokasiPojo> getDatatable(int page, int limit, String sortField, String sortOrder);

    DataResponse<LokasiPojo> findOne(String id);

    DataResponse<LokasiPojo> create(LokasiPojo lokasiPojo);

    DataResponse<LokasiPojo> update(String id, LokasiPojo lokasiPojo);

    DefaultResponse delete(String id);
}
