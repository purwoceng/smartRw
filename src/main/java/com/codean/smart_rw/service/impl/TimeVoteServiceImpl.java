package com.codean.smart_rw.service.impl;

import com.codean.smart_rw.exception.custom.NotFoundException;
import com.codean.smart_rw.mapper.TimeVoteMapper;
import com.codean.smart_rw.model.pojo.TimeVotePojo;
import com.codean.smart_rw.model.response.*;
import com.codean.smart_rw.service.TimeVoteService;
import com.codean.smart_rw.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TimeVoteServiceImpl implements TimeVoteService {
    private final TimeVoteMapper timeVoteMapper;

    private static final String SUCCESS = "Success";

    private static final Logger log = LogManager.getLogger(TimeVoteServiceImpl.class);


    @Override
    public DatatableResponse<TimeVotePojo> getDatatable(int page, int limit, String sortField, String sortOrder) {
        try {
            Map<String, String> allowedOrder = new HashMap<>();
            allowedOrder.put("id", "time_vote_id");
            String sortColumn = "time_vote_id";
            if (allowedOrder.containsKey(sortField)) {
                sortColumn = allowedOrder.getOrDefault(sortField, null);
            }
            String sortType = Objects.equals(sortOrder, "DESC") ? "DESC" : "ASC";

            int offset = (page - 1) * limit;
            List<TimeVotePojo> pageResult = timeVoteMapper.findAll(offset, limit, sortColumn, sortType);

            PageDataResponse<TimeVotePojo> data = new PageDataResponse<>(page, limit, pageResult.size(), pageResult);

            return new DatatableResponse<>(
                    SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), data
            );
        } catch (Exception e) {
            log.error("Error when get datatable time vote .", e);
            throw e;
        }
    }

    @Override
    public DataResponse<TimeVotePojo> create(TimeVotePojo timeVotePojo) {
        try {
            timeVotePojo.setCreatedAt(new DateHelper().getCurrentTimestamp());
            timeVotePojo.setTimeVoteId(UUID.randomUUID().toString());
            timeVoteMapper.insert(timeVotePojo);

            TimeVotePojo data = timeVoteMapper.findById(timeVotePojo.getTimeVoteId());
            return new DataResponse<>(SUCCESS, ResponseMessage.DATA_CREATED,  HttpStatus.OK.value(), data);
        } catch (Exception e) {
            log.error("Error when create a barang.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<TimeVotePojo> update(String id, TimeVotePojo timeVotePojo) {
        try {
            timeVotePojo.setTimeVoteId(id);
            timeVoteMapper.update(timeVotePojo);
            TimeVotePojo data = timeVoteMapper.findById(id);
            if ( data != null ) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_UPDATED, HttpStatus.OK.value(), data);
            } else {
                throw  new NotFoundException("time vote  tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when update a entitas.", e);
            throw e;
        }
    }

    @Override
    public DefaultResponse delete(String id){
        try{
            timeVoteMapper.delete(id);
            return new DefaultResponse(SUCCESS,ResponseMessage.DATA_DELETED,HttpStatus.OK.value());
        } catch (Exception e){
            log.error("Error when delete a time vote",e);
            throw e;
        }
    }

}
