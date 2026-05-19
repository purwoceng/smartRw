package com.codean.smart_rw.service;

import com.codean.smart_rw.model.pojo.TimeVotePojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;


public interface TimeVoteService {
    DatatableResponse<TimeVotePojo> getDatatable(int page, int limit, String sortField, String sortOrder);

    DataResponse<TimeVotePojo> create(TimeVotePojo timeVotePojo);

    DataResponse<TimeVotePojo> update(String id,TimeVotePojo timeVotePojo);

    DefaultResponse delete(String id);
}
