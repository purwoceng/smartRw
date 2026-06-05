package com.codean.smart_rw.service.impl;

import com.codean.smart_rw.config.CustomUsersDetails;
import com.codean.smart_rw.config.JwtService;
import com.codean.smart_rw.exception.custom.NotFoundException;
import com.codean.smart_rw.mapper.LogNotifikasiMapper;
import com.codean.smart_rw.mapper.UsersMapper;
import com.codean.smart_rw.model.dto.ActivationUsersRequest;
import com.codean.smart_rw.model.dto.CreateUsersRequest;
import com.codean.smart_rw.model.dto.LoginUsersDTO;
import com.codean.smart_rw.model.pojo.LogNotifikasiPojo;
import com.codean.smart_rw.model.pojo.UserRoles;
import com.codean.smart_rw.model.pojo.UsersPojo;
import com.codean.smart_rw.model.response.*;
import com.codean.smart_rw.service.TelegramService;
import com.codean.smart_rw.service.UsersService;
import com.codean.smart_rw.util.DateHelper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersMapper usersMapper;

    private  final TelegramService telegramService;

    private final LogNotifikasiMapper logNotifikasiMapper;

    private final ModelMapper modelMapper = new ModelMapper();

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private static final String SUCCESS = "Success";

    private static final Logger log = LogManager.getLogger(UsersServiceImpl.class);

    @Override
    public DatatableResponse<UsersPojo> getDatatable(int page, int limit, String sortField, String sortOrder) {
        try {
            Map<String, String> allowedOrder = new HashMap<>();
            allowedOrder.put("id", "user_id");
            String sortColumn = "user_id";
            if (allowedOrder.containsKey(sortField)) {
                sortColumn = allowedOrder.getOrDefault(sortField, null);
            }
            String sortType = Objects.equals(sortOrder, "DESC") ? "DESC" : "ASC";

            int offset = (page - 1) * limit;
            List<UsersPojo> pageResult = usersMapper.findAll(offset, limit, sortColumn, sortType);
            int total = usersMapper.totalData();

            PageDataResponse<UsersPojo> data = new PageDataResponse<>(page, limit, total, pageResult);

            return new DatatableResponse<>(
                    SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), data
            );
        } catch (Exception e) {
            log.error("Error when get datatable users.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<UsersPojo> createUsers (CreateUsersRequest createUsersRequest) {
        LogNotifikasiPojo logNot = new LogNotifikasiPojo();
        logNot.setLogId(UUID.randomUUID().toString());
        logNot.setChannel("Telegram");
        logNot.setSentAt(new DateHelper().getCurrentTimestamp());
        logNot.setJenisNotifikasi("Activation User");

        try {
            UsersPojo convert = postDTOCreateUsers(createUsersRequest);
            usersMapper.insertUsers(convert);

            UsersPojo data = usersMapper.findById(convert.getUserId());

            logNot.setUserId(convert.getUserId());

            String umurText = "-";
            if (data.getTanggalLahir() != null) {
                Period umur = Period.between(
                        data.getTanggalLahir(),
                        LocalDate.now()
                );
                umurText = String.format(
                        "%d tahun %d bulan %d hari",
                        umur.getYears(),
                        umur.getMonths(),
                        umur.getDays()
                );
            }


            //notifikasi ke telegram
            if(data.getChatId() !=null && !data.getChatId().isEmpty()){
                String message = String.format(
                        "Halo 👋\n\n" +
                                "Anda berhasil ditambahkan ke data RW.\n" +
                                "Nama : %s\n" +
                                "NIK  : %s\n\n" +
                                "Umur : %s \n\n" +
                                "Terima kasih.",
                        data.getNama(),
                        data.getNik(),
                        umurText
                );

                if (telegramService.isChatIdValid(data.getChatId())) {
                    String response = telegramService.sendMessage(data.getChatId(), message);
                    logNot.setStatus("Success");
                    logNot.setResponseTelegram(response);
                }

            }
            return new DataResponse<>(SUCCESS, ResponseMessage.DATA_CREATED, HttpStatus.OK.value(), data);
        } catch (Exception e) {
            log.error("Error when create a users.", e);
            logNot.setStatus("Failed");
            logNot.setResponseTelegram(e.getMessage());

            throw e;
        }finally {
            logNotifikasiMapper.insert(logNot);
        }
    }

    @Override
    public DataResponse<UsersPojo> activationUsers(String id, ActivationUsersRequest activationUsersRequest){
        try {
            UsersPojo user = usersMapper.findById(id);

            if (user == null) {
                throw new NotFoundException("User tidak ketemu");
            }

            UsersPojo activeUser = postDTOActivationUsers(activationUsersRequest);
            activeUser.setUserId(id);

            usersMapper.activationUsers(activeUser);

            if(activationUsersRequest.getRoleId() !=null){
                for(String roleId: activationUsersRequest.getRoleId()){
                    UserRoles userRole = new UserRoles();
                    userRole.setUserRoleId(UUID.randomUUID().toString());
                    userRole.setUserId(activeUser.getUserId());
                    userRole.setRoleId(roleId);
                    usersMapper.insertUserRole(userRole);
                }
            }
            return new DataResponse<>(SUCCESS, ResponseMessage.DATA_UPDATED, HttpStatus.OK.value(), user);
        }catch (Exception e){
            log.error("Error when activation a users.", e);
            throw e;
        }

    }


    @Override
    public DefaultResponse delete(String id) {
        try {
            usersMapper.delete(id);
            return new DefaultResponse(SUCCESS, ResponseMessage.DATA_DELETED,HttpStatus.OK.value());
        } catch (Exception e) {
            log.error("Error when delete a barang.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<LoginResponse> login(LoginUsersDTO loginUsersDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUsersDTO.getEmail(),
                            loginUsersDTO.getPassword()
                    )
            );

            CustomUsersDetails userDetails =
                    (CustomUsersDetails) authentication.getPrincipal();

            String jwtToken = jwtService.generateToken(userDetails);

            return new DataResponse<>(SUCCESS, ResponseMessage.LOGIN_SUCCESS, HttpStatus.OK.value(), new LoginResponse(jwtToken));
        }catch (Exception e){
            throw new NotFoundException("user tidak ketemu");
        }
    }

    @Override
    public DataResponse<UsersPojo> findOne(String id) {
        try {
            UsersPojo data = usersMapper.findById(id);
            if ( data != null ) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_FETCHED,  HttpStatus.OK.value(), data);
            } else {
                throw new NotFoundException("User tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when get detail users.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<UsersPojo> updateUsers(String id, UsersPojo usersPojo) {
        try {
            usersPojo.setUserId(id);
            usersMapper.updateUsers(usersPojo);
            UsersPojo data = usersMapper.findById(id);
            if ( data != null ) {
                return new DataResponse<>(SUCCESS, ResponseMessage.DATA_UPDATED, HttpStatus.OK.value(), data);
            } else {
                throw  new NotFoundException("Users tidak ketemu");
            }
        } catch (Exception e) {
            log.error("Error when update a entitas.", e);
            throw e;
        }
    }

//    @Override
//    public void generateExcel(HttpServletResponse response) throws IOException{
//        List<UsersPojo> usersPojos = usersMapper.findAll();
//    }


    //== utilities
    public UsersPojo postDTOCreateUsers(CreateUsersRequest createUsersRequest){
        UsersPojo usersPojo = modelMapper.map(createUsersRequest, UsersPojo.class);
        usersPojo.setUserId(UUID.randomUUID().toString());
        usersPojo.setCreatedAt(new DateHelper().getCurrentTimestamp());
        return usersPojo;
    }

    public UsersPojo postDTOActivationUsers(ActivationUsersRequest activationUsersRequest){
        UsersPojo users = modelMapper.map(activationUsersRequest,UsersPojo.class);
        users.setPassword(passwordEncoder.encode(activationUsersRequest.getPassword()));
        return users;
    }

}
