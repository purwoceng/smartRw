package com.codean.smart_rw.service.impl;

import com.codean.smart_rw.exception.custom.NotFoundException;
import com.codean.smart_rw.mapper.AsetMapper;
import com.codean.smart_rw.model.pojo.AsetPojo;
import com.codean.smart_rw.model.response.*;
import com.codean.smart_rw.service.AsetService;
import com.codean.smart_rw.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AsetServiceImpl implements AsetService {
    private final AsetMapper asetMapper;

    private static final String SUCCESS = "Success";

    private static final Logger log = LogManager.getLogger(AsetServiceImpl.class);

    @Override
    public DatatableResponse<AsetPojo> getDatatable(int page, int limit, String sortField, String sortOrder) {
        try {
            Map<String, String> allowedOrder = new HashMap<>();
            allowedOrder.put("id", "aset_id");
            String sortColumn = "aset_id";
            if (allowedOrder.containsKey(sortField)) {
                sortColumn = allowedOrder.getOrDefault(sortField, null);
            }
            String sortType = Objects.equals(sortOrder, "DESC") ? "DESC" : "ASC";

            int offset = (page - 1) * limit;
            List<AsetPojo> pageResult = asetMapper.findAll(offset, limit, sortColumn, sortType);

            PageDataResponse<AsetPojo> data = new PageDataResponse<>(page, limit, pageResult.size(), pageResult);

            return new DatatableResponse<>(
                    SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), data
            );
        } catch (Exception e) {
            log.error("Error when get datatable barang.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<AsetPojo> findOne(String id) {
        try {
            AsetPojo data = asetMapper.findById(id);
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_FETCHED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("Data aset tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when get detail aset.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<AsetPojo> create(AsetPojo asetPojo) {
        try {
            asetPojo.setCreatedAt(new DateHelper().getCurrentTimestamp());
            asetPojo.setAsetId(UUID.randomUUID().toString());
            asetMapper.insert(asetPojo);

            AsetPojo data = asetMapper.findById(asetPojo.getAsetId());
            return new DataResponse<>(SUCCESS, ResponseMessage.DATA_CREATED,HttpStatus.OK.value(), data);
        } catch (Exception e) {
            log.error("Error when create a aset.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<AsetPojo> update(String id, AsetPojo asetPojo) {
        try {
            asetPojo.setAsetId(id);
            asetPojo.setUpdatedAt(new DateHelper().getCurrentTimestamp());
            asetMapper.update(asetPojo);

            AsetPojo data = asetMapper.findById(id);
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_UPDATED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("data aset tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when update a aset.", e);
            throw e;
        }
    }

    @Override
    public DefaultResponse delete(String id) {
        try {
            asetMapper.delete(id);
            return new DefaultResponse(SUCCESS, ResponseMessage.DATA_DELETED,  HttpStatus.OK.value());
        } catch (Exception e) {
            log.error("Error when delete a aset.", e);
            throw e;
        }
    }
}
