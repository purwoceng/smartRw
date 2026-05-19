package com.codean.smart_rw.service;

import com.codean.smart_rw.model.pojo.PengumumanPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;

import java.io.IOException;

public interface PengumumanService {
    DatatableResponse<PengumumanPojo> getDatatable(int page, int limit, String sortField, String sortOrder);

    DataResponse<PengumumanPojo> findOne(String id);

    DataResponse<PengumumanPojo> create(PengumumanPojo pengumumanPojo) throws IOException;
}
