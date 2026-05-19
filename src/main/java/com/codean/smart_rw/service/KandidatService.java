package com.codean.smart_rw.service;

import com.codean.smart_rw.model.pojo.KandidatPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;

import java.io.IOException;

public interface KandidatService {
    DatatableResponse<KandidatPojo> getDatatable(int page, int limit, String sortField, String sortOrder);

    DataResponse<KandidatPojo> create(KandidatPojo kandidatPojo) throws IOException;

    DefaultResponse delete(String id);

    DataResponse<KandidatPojo> findOne(String id);
}
