package com.codean.smart_rw.service;

import com.codean.smart_rw.model.pojo.KasJimpitanPojo;
import com.codean.smart_rw.model.pojo.TotalSaldoKasPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;

public interface KasJimpitanService {
    DatatableResponse<KasJimpitanPojo> getDatatable(int page, int limit, String sortField, String sortOrder);

    DataResponse<KasJimpitanPojo> findOne(String id);

    DataResponse<TotalSaldoKasPojo> findTotalSaldo();

    DataResponse<KasJimpitanPojo> create(KasJimpitanPojo kasJimpitanPojo);

    DataResponse<KasJimpitanPojo> update(String id, KasJimpitanPojo kasJimpitanPojo);

    DefaultResponse delete(String id);
}
