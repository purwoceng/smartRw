package com.codean.smart_rw.service.impl;

import com.codean.smart_rw.exception.custom.NotFoundException;
import com.codean.smart_rw.mapper.LogNotifikasiMapper;
import com.codean.smart_rw.mapper.PengumumanMapper;
import com.codean.smart_rw.mapper.UsersMapper;
import com.codean.smart_rw.messaging.NotificationProducer;
import com.codean.smart_rw.model.dto.NotifikasiMessage;
import com.codean.smart_rw.model.pojo.LogNotifikasiPojo;
import com.codean.smart_rw.model.pojo.PengumumanPojo;
import com.codean.smart_rw.model.pojo.UsersPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;
import com.codean.smart_rw.model.response.PageDataResponse;
import com.codean.smart_rw.model.response.ResponseMessage;
import com.codean.smart_rw.service.PengumumanService;
import com.codean.smart_rw.service.TelegramService;
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
public class PengumumanServiceImpl implements PengumumanService {
    private final PengumumanMapper pengumumanMapper;

    private final UsersMapper usersMapper;

    private final LogNotifikasiMapper logNotifikasiMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final TelegramService telegramService;

    private final NotificationProducer notificationProducer;

    private static final String SUCCESS = "Success";

    private static final Logger log = LogManager.getLogger(PengumumanServiceImpl.class);

    @Override
    public DatatableResponse<PengumumanPojo> getDatatable(int page, int limit, String sortField, String sortOrder) {
        try {
            Map<String, String> allowedOrder = new HashMap<>();
            allowedOrder.put("id", "pengumuman_id");
            String sortColumn = "pengumuman_id";
            if (allowedOrder.containsKey(sortField)) {
                sortColumn = allowedOrder.getOrDefault(sortField, null);
            }
            String sortType = Objects.equals(sortOrder, "DESC") ? "DESC" : "ASC";

            int offset = (page - 1) * limit;
            List<PengumumanPojo> pageResult = pengumumanMapper.findAll(offset, limit, sortColumn, sortType);

            PageDataResponse<PengumumanPojo> data = new PageDataResponse<>(page, limit, pageResult.size(), pageResult);

            return new DatatableResponse<>(
                    SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), data
            );
        } catch (Exception e) {
            log.error("Error when get datatable pengumuman.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<PengumumanPojo> findOne(String id) {
        try {
            PengumumanPojo data = pengumumanMapper.findById(id);
            if (data != null) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_FETCHED,HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("Data pengumuman tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when get detail data pengumuman.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<PengumumanPojo> create(PengumumanPojo pengumumanPojo) throws IOException{
        try {
            pengumumanPojo.setCreatedAt(new DateHelper().getCurrentTimestamp());
            pengumumanPojo.setPengumumanId(UUID.randomUUID().toString());
            String image = saveFile(pengumumanPojo.getMultipartFile());
            pengumumanPojo.setImage(buildImageUrl(image));
            pengumumanMapper.insert(pengumumanPojo);

            List<UsersPojo> users = usersMapper.findUserWithChatId();
            log.info("Jumlah user chatId = {}", users.size());


            for (UsersPojo u: users){
                if(u.getChatId() == null || u.getChatId().isBlank()) continue;

                NotifikasiMessage msg = new NotifikasiMessage();
                msg.setChatId(u.getChatId());
                msg.setPesan(pengumumanPojo.getDeskripsi());
                msg.setUserId(u.getUserId());
                msg.setJenis("Pengumuman");
                msg.setPengumumanId(pengumumanPojo.getPengumumanId());
                msg.setImageUrl(pengumumanPojo.getImage());

                notificationProducer.publish(msg);
            }
            PengumumanPojo data = pengumumanMapper.findById(pengumumanPojo.getPengumumanId());
            return new DataResponse<>(SUCCESS, ResponseMessage.DATA_CREATED,HttpStatus.OK.value(), data);
        } catch (Exception e) {
            log.error("Error when create a penguman.", e);
            throw e;
        }
    }

    // utilities
    public String saveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()){
            return null;
        }

        Path upload = Paths.get(uploadDir).toAbsolutePath().normalize();

        if(!Files.exists(upload)){
            Files.createDirectories(upload);
        }

        String fileName = System.currentTimeMillis() + "-" + "pengumuman-"+ multipartFile.getOriginalFilename();

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

    @Override
    public DefaultResponse delete(String id) {
        try {
            pengumumanMapper.delete(id);
            return new DefaultResponse(SUCCESS, ResponseMessage.DATA_DELETED, HttpStatus.OK.value());
        } catch (Exception e) {
            log.error("Error when delete a pengumuman.", e);
            throw e;
        }
    }
}
