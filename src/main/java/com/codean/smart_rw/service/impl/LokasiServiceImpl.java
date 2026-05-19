package com.codean.smart_rw.service.impl;

import com.codean.smart_rw.exception.custom.NotFoundException;
import com.codean.smart_rw.mapper.LokasiMapper;
import com.codean.smart_rw.model.pojo.LokasiPojo;
import com.codean.smart_rw.model.response.*;
import com.codean.smart_rw.service.LokasiService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LokasiServiceImpl implements LokasiService {
    private final LokasiMapper lokasiMapper;

    private static final String SUCCESS = "Success";

    private static final Logger log = LogManager.getLogger(LokasiServiceImpl.class);

    @Override
    public DatatableResponse<LokasiPojo> getDatatable(int page, int limit, String sortField, String sortOrder) {
        try {
            Map<String, String> allowedOrder = new HashMap<>();
            allowedOrder.put("id", "lokasi_id");
            String sortColumn = "lokasi_id";
            if (allowedOrder.containsKey(sortField)) {
                sortColumn = allowedOrder.getOrDefault(sortField, null);
            }
            String sortType = Objects.equals(sortOrder, "DESC") ? "DESC" : "ASC";

            int offset = (page - 1) * limit;
            List<LokasiPojo> pageResult = lokasiMapper.findAll(offset, limit, sortColumn, sortType);

            PageDataResponse<LokasiPojo> data = new PageDataResponse<>(page, limit, pageResult.size(), pageResult);

            return new DatatableResponse<>(
                    SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), data
            );
        } catch (Exception e) {
            log.error("Error when get datatable lokasi ronda.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<LokasiPojo> findOne(String id) {
        try {
            LokasiPojo data = lokasiMapper.findById(id);
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_FETCHED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("Data Lokasi Ronda tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when get detail lokasi ronda.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<LokasiPojo> create(LokasiPojo lokasiPojo) {
        try {
            lokasiPojo.setLokasiId(UUID.randomUUID().toString());
            lokasiMapper.insert(lokasiPojo);

            LokasiPojo data = lokasiMapper.findById(lokasiPojo.getLokasiId());
            return new DataResponse<>(SUCCESS, ResponseMessage.DATA_CREATED,HttpStatus.OK.value(), data);
        } catch (Exception e) {
            log.error("Error when create a Lokasi Ronda.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<LokasiPojo> update(String id, LokasiPojo lokasiPojo) {
        try {
            lokasiPojo.setLokasiId(id);
            lokasiMapper.update(lokasiPojo);

            LokasiPojo data = lokasiMapper.findById(id);
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_UPDATED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("data lokasi ronda tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when update a lokasi ronda.", e);
            throw e;
        }
    }

    @Override
    public DefaultResponse delete(String id) {
        try {
            lokasiMapper.delete(id);
            return new DefaultResponse(SUCCESS, ResponseMessage.DATA_DELETED,  HttpStatus.OK.value());
        } catch (Exception e) {
            log.error("Error when delete a lokasi ronda.", e);
            throw e;
        }
    }
}
