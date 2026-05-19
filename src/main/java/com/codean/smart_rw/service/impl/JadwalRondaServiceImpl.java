package com.codean.smart_rw.service.impl;

import com.codean.smart_rw.exception.custom.NotFoundException;
import com.codean.smart_rw.mapper.JadwalRondaMapper;
import com.codean.smart_rw.mapper.JadwalRondaUserMapper;
import com.codean.smart_rw.mapper.LokasiMapper;
import com.codean.smart_rw.mapper.UsersMapper;
import com.codean.smart_rw.model.pojo.JadwalRondaPojo;
import com.codean.smart_rw.model.pojo.JadwalRondaUserPojo;
import com.codean.smart_rw.model.pojo.LokasiPojo;
import com.codean.smart_rw.model.pojo.UsersPojo;
import com.codean.smart_rw.model.response.*;
import com.codean.smart_rw.service.JadwalRondaService;
import com.codean.smart_rw.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JadwalRondaServiceImpl implements JadwalRondaService
{
    private final JadwalRondaMapper jadwalRondaMapper;

    private final JadwalRondaUserMapper jadwalRondaUserMapper;

    private final LokasiMapper lokasiMapper;

    private final UsersMapper usersMapper;

    private static final String SUCCESS = "Success";

    private static final Logger log = LogManager.getLogger(JadwalRondaServiceImpl.class);

    @Override
    public DatatableResponse<JadwalRondaPojo> getDatatable(int page, int limit, String sortField, String sortOrder) {
        try {
            Map<String, String> allowedOrder = new HashMap<>();
            allowedOrder.put("id", "jadwal_id");
            String sortColumn = "jadwal_id";
            if (allowedOrder.containsKey(sortField)) {
                sortColumn = allowedOrder.getOrDefault(sortField, null);
            }
            String sortType = Objects.equals(sortOrder, "DESC") ? "DESC" : "ASC";

            int offset = (page - 1) * limit;
            List<JadwalRondaPojo> pageResult = jadwalRondaMapper.findAll(offset, limit, sortColumn, sortType);

            PageDataResponse<JadwalRondaPojo> data = new PageDataResponse<>(page, limit, pageResult.size(), pageResult);

            return new DatatableResponse<>(
                    SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), data
            );
        } catch (Exception e) {
            log.error("Error when get datatable jadwal ronda.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<JadwalRondaPojo> findOne(String id) {
        try {
            JadwalRondaPojo data = jadwalRondaMapper.findById(id);
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_FETCHED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("Data Jadwal ronda tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when get detail jadwal ronda.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<JadwalRondaPojo> create(JadwalRondaPojo jadwalRondaPojo) {
        try {
            LokasiPojo lokasi = lokasiMapper.findById(jadwalRondaPojo.getLokasiId());

            if (lokasi == null){
                throw new NotFoundException("Maaf lokasi ronda tidak ada di sistem");
            }
            jadwalRondaPojo.setCreatedAt(new DateHelper().getCurrentTimestamp());
            jadwalRondaPojo.setJadwalId(UUID.randomUUID().toString());
            jadwalRondaMapper.insert(jadwalRondaPojo);

            for(JadwalRondaUserPojo j : jadwalRondaPojo.getUserId()){
                UsersPojo users = usersMapper.findById(j.getUserId());

                if (users == null){
                    throw new NotFoundException("user tidak ada di dalam sistem");
                }

                j.setJadwalWargaId(UUID.randomUUID().toString());
                j.setJadwalId(jadwalRondaPojo.getJadwalId());
                j.setUserId(j.getUserId());
                jadwalRondaUserMapper.insert(j);
            }

            JadwalRondaPojo data = jadwalRondaMapper.findById(jadwalRondaPojo.getJadwalId());
            return new DataResponse<>(SUCCESS, ResponseMessage.DATA_CREATED,HttpStatus.OK.value(), data);
        } catch (Exception e) {
            log.error("Error when create a jadwal ronda.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<JadwalRondaPojo> update(String id, JadwalRondaPojo jadwalRondaPojo) {
        try {
            jadwalRondaPojo.setJadwalId(id);
            jadwalRondaPojo.setUpdatedAt(new DateHelper().getCurrentTimestamp());
            jadwalRondaMapper.update(jadwalRondaPojo);

            List<JadwalRondaUserPojo> userlama = jadwalRondaUserMapper.findByJadwalId(id);

            if(userlama == null){
                throw new NotFoundException("user tidak ketemu");
            }

            jadwalRondaUserMapper.deleteByJadwalId(id);

            for(JadwalRondaUserPojo j : jadwalRondaPojo.getUserId()){
                UsersPojo users = usersMapper.findById(j.getUserId());

                if (users == null){
                    throw new NotFoundException("user tidak ada di dalam sistem");
                }

                j.setJadwalWargaId(UUID.randomUUID().toString());
                j.setJadwalId(jadwalRondaPojo.getJadwalId());
                j.setUserId(j.getUserId());
                jadwalRondaUserMapper.insert(j);
            }



            JadwalRondaPojo data = jadwalRondaMapper.findById(id);
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_UPDATED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("data jadwal ronda tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when update a jadwal ronda.", e);
            throw e;
        }
    }

    @Override
    public DefaultResponse delete(String id) {
        try {
            jadwalRondaMapper.delete(id);
            return new DefaultResponse(SUCCESS, ResponseMessage.DATA_DELETED,  HttpStatus.OK.value());
        } catch (Exception e) {
            log.error("Error when delete a jadwal ronda.", e);
            throw e;
        }
    }
}
