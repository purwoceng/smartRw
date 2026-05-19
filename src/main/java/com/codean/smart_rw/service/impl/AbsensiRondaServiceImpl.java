package com.codean.smart_rw.service.impl;

import com.codean.smart_rw.config.CustomUsersDetails;
import com.codean.smart_rw.exception.custom.NotFoundException;
import com.codean.smart_rw.mapper.AbsensiRondaMapper;
import com.codean.smart_rw.mapper.JadwalRondaUserMapper;
import com.codean.smart_rw.model.enumStatus.HariRondaEnum;
import com.codean.smart_rw.model.pojo.AbsensiRondaPojo;
import com.codean.smart_rw.model.response.*;
import com.codean.smart_rw.service.AbsensiRondaService;
import com.codean.smart_rw.util.DateHelper;
import com.codean.smart_rw.util.HaversineUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AbsensiRondaServiceImpl implements AbsensiRondaService {
    private final AbsensiRondaMapper absensiRondaMapper;

    private final JadwalRondaUserMapper jadwalRondaUserMapper;

    private static final String SUCCESS = "Success";

    private static final Logger log = LogManager.getLogger(AbsensiRondaServiceImpl.class);

    @Override
    public DatatableResponse<AbsensiRondaPojo> getDatatable(int page, int limit, String sortField, String sortOrder) {
        try {
            Map<String, String> allowedOrder = new HashMap<>();
            allowedOrder.put("id", "absensi_id");
            String sortColumn = "absensi_id";
            if (allowedOrder.containsKey(sortField)) {
                sortColumn = allowedOrder.getOrDefault(sortField, null);
            }
            String sortType = Objects.equals(sortOrder, "DESC") ? "DESC" : "ASC";

            int offset = (page - 1) * limit;
            List<AbsensiRondaPojo> pageResult = absensiRondaMapper.findAll(offset, limit, sortColumn, sortType);

            PageDataResponse<AbsensiRondaPojo> data = new PageDataResponse<>(page, limit, pageResult.size(), pageResult);

            return new DatatableResponse<>(
                    SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), data
            );
        } catch (Exception e) {
            log.error("Error when get datatable absensi ronda.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<AbsensiRondaPojo> findOne(String id) {
        try {
            AbsensiRondaPojo data = absensiRondaMapper.findById(id);
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_FETCHED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("Data absensi ronda tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when get detail absensi ronda.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<AbsensiRondaPojo> create(AbsensiRondaPojo absensiRondaPojo) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUsersDetails users = (CustomUsersDetails) auth.getPrincipal();
            String userId = users.getUserId();

            HariRondaEnum hari = HariRondaEnum.fromDayOfWeek(LocalDate.now().getDayOfWeek());

            AbsensiRondaPojo jadwal = jadwalRondaUserMapper.findJadwalUser(userId, hari.name());

            if (jadwal == null){
                throw new NotFoundException("Tidak ada jadwal ronda untuk anda hari ini");
            }

            if(absensiRondaMapper.cekAbsen(jadwal.getJadwalWargaId())){
                throw new NotFoundException("Sudah absen hari ini");
            }

            double jarak = HaversineUtil.hitungJarak(absensiRondaPojo.getLatitude(),absensiRondaPojo.getLongitude(),jadwal.getLatitude(),jadwal.getLongitude());

            if (jarak > jadwal.getRadius()){
                throw new NotFoundException("Diluar radius lokasi yang ditentukan");
            }

            absensiRondaPojo.setCreatedAt(new DateHelper().getCurrentTimestamp());
            absensiRondaPojo.setJamMasuk(LocalTime.now());
            absensiRondaPojo.setJarak((int)jarak);
            absensiRondaPojo.setAbsensiId(UUID.randomUUID().toString());
            absensiRondaMapper.insert(absensiRondaPojo);

            AbsensiRondaPojo data = absensiRondaMapper.findById(absensiRondaPojo.getAbsensiId());
            return new DataResponse<>(SUCCESS, ResponseMessage.DATA_CREATED,HttpStatus.OK.value(), data);
        } catch (Exception e) {
            log.error("Error when create a absensi ronda.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<AbsensiRondaPojo> update(String id, AbsensiRondaPojo absensiRondaPojo) {
        try {
            absensiRondaPojo.setAbsensiId(id);
            absensiRondaPojo.setUpdatedAt(new DateHelper().getCurrentTimestamp());
            absensiRondaMapper.update(absensiRondaPojo);

            AbsensiRondaPojo data = absensiRondaMapper.findById(id);
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_UPDATED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("data absensi ronda tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when update a absensi ronda.", e);
            throw e;
        }
    }

    @Override
    public DefaultResponse delete(String id) {
        try {
            absensiRondaMapper.delete(id);
            return new DefaultResponse(SUCCESS, ResponseMessage.DATA_DELETED,  HttpStatus.OK.value());
        } catch (Exception e) {
            log.error("Error when delete a absensi ronda.", e);
            throw e;
        }
    }
}
