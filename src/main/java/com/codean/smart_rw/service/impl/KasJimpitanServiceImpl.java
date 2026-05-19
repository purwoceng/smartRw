package com.codean.smart_rw.service.impl;

import com.codean.smart_rw.config.CustomUsersDetails;
import com.codean.smart_rw.exception.custom.NotFoundException;
import com.codean.smart_rw.mapper.KasJimpitanMapper;
import com.codean.smart_rw.model.pojo.KasJimpitanPojo;
import com.codean.smart_rw.model.pojo.TotalSaldoKasPojo;
import com.codean.smart_rw.model.response.*;
import com.codean.smart_rw.service.KasJimpitanService;
import com.codean.smart_rw.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class KasJimpitanServiceImpl implements KasJimpitanService {
    private final KasJimpitanMapper kasJimpitanMapper;

    private static final String SUCCESS = "Success";

    private static final Logger log = LogManager.getLogger(KasJimpitanServiceImpl.class);

    @Override
    public DatatableResponse<KasJimpitanPojo> getDatatable(int page, int limit, String sortField, String sortOrder) {
        try {
            Map<String, String> allowedOrder = new HashMap<>();
            allowedOrder.put("created_at", "created_at");
            String sortColumn = "created_at";
            if (allowedOrder.containsKey(sortField)) {
                sortColumn = allowedOrder.getOrDefault(sortField, null);
            }
            String sortType = Objects.equals(sortOrder, "DESC") ? "DESC" : "ASC";

            int offset = (page - 1) * limit;
            List<KasJimpitanPojo> pageResult = kasJimpitanMapper.findAll(offset, limit, sortColumn, sortType);

            PageDataResponse<KasJimpitanPojo> data = new PageDataResponse<>(page, limit, pageResult.size(), pageResult);

            return new DatatableResponse<>(
                    SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), data
            );
        } catch (Exception e) {
            log.error("Error when get datatable kas jimpitan.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<KasJimpitanPojo> findOne(String id) {
        try {
            KasJimpitanPojo data = kasJimpitanMapper.findById(id);
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_FETCHED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("Data kas jimpitan tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when get detail kas jimpitan.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<TotalSaldoKasPojo> findTotalSaldo() {
        try {
            TotalSaldoKasPojo data = kasJimpitanMapper.findTotalSaldo();
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_FETCHED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("Data kas jimpitan tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when get detail kas jimpitan.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<KasJimpitanPojo> create(KasJimpitanPojo kasJimpitanPojo) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUsersDetails users = (CustomUsersDetails) auth.getPrincipal();
            String userId = users.getUserId();

            kasJimpitanPojo.setUserId(userId);
            kasJimpitanPojo.setCreatedAt(new DateHelper().getCurrentTimestamp());
            kasJimpitanPojo.setKasId(UUID.randomUUID().toString());
            kasJimpitanMapper.insert(kasJimpitanPojo);

            KasJimpitanPojo data = kasJimpitanMapper.findById(kasJimpitanPojo.getKasId());
            return new DataResponse<>(SUCCESS, ResponseMessage.DATA_CREATED,HttpStatus.OK.value(), data);
        } catch (Exception e) {
            log.error("Error when create a kas jimpitan.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<KasJimpitanPojo> update(String id, KasJimpitanPojo kasJimpitanPojo) {
        try {
            kasJimpitanPojo.setKasId(id);
            kasJimpitanPojo.setUpdatedAt(new DateHelper().getCurrentTimestamp());
            kasJimpitanMapper.update(kasJimpitanPojo);

            KasJimpitanPojo data = kasJimpitanMapper.findById(id);
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_UPDATED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("data kas jimpitan tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when update a kas jimpitan.", e);
            throw e;
        }
    }

    @Override
    public DefaultResponse delete(String id) {
        try {
            kasJimpitanMapper.delete(id);
            return new DefaultResponse(SUCCESS, ResponseMessage.DATA_DELETED,  HttpStatus.OK.value());
        } catch (Exception e) {
            log.error("Error when delete a kas jimpitan.", e);
            throw e;
        }
    }
}
