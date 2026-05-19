package com.codean.smart_rw.service;

import com.codean.smart_rw.model.pojo.VotingPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;

public interface VotingService {
    DatatableResponse<VotingPojo> getDatatable(int page, int limit, String sortField, String sortOrder);

    DataResponse<VotingPojo> create(VotingPojo votingPojo);
}
