package com.codean.smart_rw.service.impl;

import com.codean.smart_rw.config.CustomUsersDetails;
import com.codean.smart_rw.exception.custom.NotFoundException;
import com.codean.smart_rw.mapper.AsetMapper;
import com.codean.smart_rw.mapper.PeminjamanAsetMapper;
import com.codean.smart_rw.model.enumStatus.StatusAsetEnum;
import com.codean.smart_rw.model.enumStatus.StatusPeminjamanAserEnum;
import com.codean.smart_rw.model.pojo.AsetPojo;
import com.codean.smart_rw.model.pojo.DetailPeminjamanAsetPojo;
import com.codean.smart_rw.model.pojo.PeminjamanAsetPojo;
import com.codean.smart_rw.model.response.*;
import com.codean.smart_rw.service.PeminjamanAsetService;
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
public class PeminjamanAsetServiceImpl implements PeminjamanAsetService {
    private final PeminjamanAsetMapper peminjamanAsetMapper;

    private final AsetMapper asetMapper;

    private static final String SUCCESS = "Success";

    private static final Logger log = LogManager.getLogger(PeminjamanAsetServiceImpl.class);

    @Override
    public DatatableResponse<PeminjamanAsetPojo> getDatatable(int page, int limit, String sortField, String sortOrder) {
        try {
            Map<String, String> allowedOrder = new HashMap<>();
            allowedOrder.put("id", "peminjaman_id");
            String sortColumn = "peminjaman_id";
            if (allowedOrder.containsKey(sortField)) {
                sortColumn = allowedOrder.getOrDefault(sortField, null);
            }
            String sortType = Objects.equals(sortOrder, "DESC") ? "DESC" : "ASC";

            int offset = (page - 1) * limit;
            List<PeminjamanAsetPojo> pageResult = peminjamanAsetMapper.findAll(offset, limit, sortColumn, sortType);

            PageDataResponse<PeminjamanAsetPojo> data = new PageDataResponse<>(page, limit, pageResult.size(), pageResult);

            return new DatatableResponse<>(
                    SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), data
            );
        } catch (Exception e) {
            log.error("Error when get datatable barang.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<PeminjamanAsetPojo> findOne(String id) {
        try {
            PeminjamanAsetPojo data = peminjamanAsetMapper.findById(id);
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_FETCHED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("Data peminjaman tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when get detail peminjaman.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<PeminjamanAsetPojo> create(PeminjamanAsetPojo peminjamanAsetPojo) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUsersDetails users = (CustomUsersDetails) auth.getPrincipal();
            String userId = users.getUserId();

            peminjamanAsetPojo.setUserId(userId);
            peminjamanAsetPojo.setCreatedAt(new DateHelper().getCurrentTimestamp());
            peminjamanAsetPojo.setPeminjamanId(UUID.randomUUID().toString());
            peminjamanAsetMapper.insert(peminjamanAsetPojo);

            for(DetailPeminjamanAsetPojo d: peminjamanAsetPojo.getItems()){
                AsetPojo asetPojo = asetMapper.findById(d.getAsetId());

                if (asetPojo == null){
                    throw new NotFoundException("Aset tidak ada dalam data");
                }

                if (d.getJumlah() > asetPojo.getJumlah()){
                    throw new IllegalArgumentException("Jumlah peminjaman melebihi stok aset");
                }

                d.setPeminjamanDetailId(UUID.randomUUID().toString());
                d.setPeminjamanId(peminjamanAsetPojo.getPeminjamanId());
               peminjamanAsetMapper.insertDetails(d);
            }

            PeminjamanAsetPojo data = peminjamanAsetMapper.findById(peminjamanAsetPojo.getPeminjamanId());
            return new DataResponse<>(SUCCESS, ResponseMessage.DATA_CREATED,HttpStatus.OK.value(), data);
        } catch (Exception e) {
            log.error("Error when create a peminjaman aset.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<PeminjamanAsetPojo> update(String id, PeminjamanAsetPojo peminjamanAsetPojo) {
        try {
            PeminjamanAsetPojo data = peminjamanAsetMapper.findById(id);
            peminjamanAsetPojo.setPeminjamanId(id);
            peminjamanAsetPojo.setUpdatedAt(new DateHelper().getCurrentTimestamp());
            peminjamanAsetMapper.update(peminjamanAsetPojo);

            List<DetailPeminjamanAsetPojo> detailLama = peminjamanAsetMapper.findDetailByPeminjamanId(id);

            peminjamanAsetMapper.deleteDetailByPeminjamanId(id);

            for(DetailPeminjamanAsetPojo d: peminjamanAsetPojo.getItems()){
                AsetPojo asetPojo = asetMapper.findById(d.getAsetId());

                if (asetPojo == null){
                    throw new NotFoundException("Aset tidak ada dalam data");
                }

                if (d.getJumlah() > asetPojo.getJumlah()){
                    throw new IllegalArgumentException("Jumlah peminjaman melebihi stok aset");
                }

                d.setPeminjamanDetailId(UUID.randomUUID().toString());
                d.setPeminjamanId(peminjamanAsetPojo.getPeminjamanId());
                peminjamanAsetMapper.insertDetails(d);

                if(StatusPeminjamanAserEnum.DISETUJUI
                        .equals(peminjamanAsetPojo.getStatus())){
                    asetPojo.setJumlah(asetPojo.getJumlah() - d.getJumlah());
                    asetPojo.setStatus(StatusAsetEnum.DIPINJAM);
                    asetMapper.update(asetPojo);
                }
            }



            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_UPDATED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("data peminjaman tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when update a peminjaman.", e);
            throw e;
        }
    }

    @Override
    public DefaultResponse delete(String id) {
        try {
            peminjamanAsetMapper.delete(id);
            return new DefaultResponse(SUCCESS, ResponseMessage.DATA_DELETED,  HttpStatus.OK.value());
        } catch (Exception e) {
            log.error("Error when delete a peminjaman aset.", e);
            throw e;
        }
    }
}
