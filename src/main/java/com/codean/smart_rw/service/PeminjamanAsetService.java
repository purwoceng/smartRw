package com.codean.smart_rw.service;

import com.codean.smart_rw.model.pojo.PeminjamanAsetPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;

public interface PeminjamanAsetService {
    DatatableResponse<PeminjamanAsetPojo> getDatatable(int page, int limit, String sortField, String sortOrder);

    DataResponse<PeminjamanAsetPojo> findOne(String id);

    DataResponse<PeminjamanAsetPojo> create(PeminjamanAsetPojo peminjamanAsetPojo);

    DataResponse<PeminjamanAsetPojo> update(String id, PeminjamanAsetPojo peminjamanAsetPojo);

    DefaultResponse delete(String id);
}
