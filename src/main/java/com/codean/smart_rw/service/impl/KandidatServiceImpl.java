package com.codean.smart_rw.service.impl;

import com.codean.smart_rw.exception.custom.NotFoundException;
import com.codean.smart_rw.mapper.KandidatMapper;
import com.codean.smart_rw.mapper.UsersMapper;
import com.codean.smart_rw.model.pojo.KandidatPojo;
import com.codean.smart_rw.model.pojo.UsersPojo;
import com.codean.smart_rw.model.response.*;
import com.codean.smart_rw.service.KandidatService;
import com.codean.smart_rw.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class KandidatServiceImpl implements KandidatService {
    private final KandidatMapper kandidatMapper;

    private final UsersMapper usersMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final String SUCCESS = "Success";

    private static final Logger log = LogManager.getLogger(KandidatServiceImpl.class);

    @Override
    public DatatableResponse<KandidatPojo> getDatatable(int page, int limit, String sortField, String sortOrder) {
        try {
            Map<String, String> allowedOrder = new HashMap<>();
            allowedOrder.put("id", "kandidat_id");
            String sortColumn = "kandidat_id";
            if (allowedOrder.containsKey(sortField)) {
                sortColumn = allowedOrder.getOrDefault(sortField, null);
            }
            String sortType = Objects.equals(sortOrder, "DESC") ? "DESC" : "ASC";

            int offset = (page - 1) * limit;
            List<KandidatPojo> pageResult = kandidatMapper.findAll(offset, limit, sortColumn, sortType);

            PageDataResponse<KandidatPojo> data = new PageDataResponse<>(page, limit, pageResult.size(), pageResult);

            return new DatatableResponse<>(
                    SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), data
            );
        } catch (Exception e) {
            log.error("Error when get datatable kandidat .", e);
            throw e;
        }
    }

    @Override
    public DataResponse<KandidatPojo> create(KandidatPojo kandidatPojo) throws IOException{
        try {
            Optional<UsersPojo> user = usersMapper.findByNik(kandidatPojo.getNik());

            if(user.isEmpty()){
                throw new NotFoundException("nik user tidak ketemu");
            }
            kandidatPojo.setCreatedAt(new DateHelper().getCurrentTimestamp());
            kandidatPojo.setKandidatId(UUID.randomUUID().toString());
            String image = saveFile(kandidatPojo.getMultipartFile());
            kandidatPojo.setImage(buildImageUrl(image));
            kandidatPojo.setTotalVote(0);
            kandidatMapper.insert(kandidatPojo);

            KandidatPojo data = kandidatMapper.findById(kandidatPojo.getKandidatId());
            return new DataResponse<>(SUCCESS, ResponseMessage.DATA_CREATED,  HttpStatus.OK.value(), data);
        } catch (Exception e) {
            log.error("Error when create a barang.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<KandidatPojo> findOne(String id) {
        try {
            KandidatPojo data = kandidatMapper.findById(id);
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_FETCHED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("Data detail kandidat tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when get detail kandidat.", e);
            throw e;
        }
    }

    @Override
    public DefaultResponse delete(String id){
        try{
            kandidatMapper.delete(id);
            return new DefaultResponse(SUCCESS,ResponseMessage.DATA_DELETED,HttpStatus.OK.value());
        } catch (Exception e){
            log.error("Error when delete a kandidat",e);
            throw e;
        }
    }

    // utilities
    public String saveFile(MultipartFile multipartFile) throws IOException{
        if (multipartFile == null || multipartFile.isEmpty()){
            return null;
        }

        Path upload = Paths.get(uploadDir).toAbsolutePath().normalize();

        if(!Files.exists(upload)){
            Files.createDirectories(upload);
        }

        String fileName = System.currentTimeMillis() + "-" + "users-"+ multipartFile.getOriginalFilename();

        Path filePath = upload.resolve(fileName);

        String contentType = multipartFile.getContentType();

        if(!contentType.equals("image/jpeg") && !contentType.equals("image/png")){
            throw new IllegalArgumentException("Format salah hanya JPEG/PNG");
        }

        multipartFile.transferTo(filePath.toFile());

        return fileName;

    }

    private String buildImageUrl(String fileName){
        if (fileName == null){
            return null;
        }

        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/rw/").path(fileName).toUriString();
    }

}
