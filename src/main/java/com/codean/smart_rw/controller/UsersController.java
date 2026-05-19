package com.codean.smart_rw.controller;

import com.codean.smart_rw.model.dto.ActivationUsersRequest;
import com.codean.smart_rw.model.dto.CreateUsersRequest;
import com.codean.smart_rw.model.dto.LoginUsersDTO;
import com.codean.smart_rw.model.pojo.UsersPojo;
import com.codean.smart_rw.model.response.*;
import com.codean.smart_rw.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/smart-rw/users/")
@Tag(name = "Users Service", description = "API Collections for Users")
public class UsersController {
    private final UsersService usersService;

    @GetMapping
    @Operation(
            summary = "Get Users List",
            description = "fetches all users from data source"
    )
    public ResponseEntity<DatatableResponse<UsersPojo>> getDatatable(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "id", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) String sortOrder
    ) {
        DatatableResponse<UsersPojo> list = usersService.getDatatable(page, limit, sortField, sortOrder);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = "/findOne")
    @Operation(
            summary = "Get Detailed Users Data",
            description = "fetch detailed user data from data source"
    )
    public ResponseEntity<DataResponse<UsersPojo>> getById(
            @RequestParam String id
    ) {
        DataResponse<UsersPojo> data = usersService.findOne(id);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new users",
            description = "Create a new users and save it into the data source.\n\nStatus User(TETAP,PENDATANG,PINDAH atau PINDAH).\n\nJenis Kelamin(LAKI_LAKI,PEREMPUAN)"
    )
    public ResponseEntity<DataResponse<UsersPojo>> create(@Valid @RequestBody CreateUsersRequest createUsersRequest,
                                                          HttpServletRequest request
    ) {
        DataResponse<UsersPojo> data = usersService.createUsers(createUsersRequest);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping(value = "auth/login/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Login a users"
    )
    public ResponseEntity<DataResponse<LoginResponse>> login(@Valid @RequestBody LoginUsersDTO loginUsersDTO,
                                                             HttpServletRequest request
    ) {
        DataResponse<LoginResponse> data = usersService.login(loginUsersDTO);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping(value = "{id}/active/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Activation a  users",
            description = "Activation a users and save it into the data source"
    )
    public ResponseEntity<DataResponse<UsersPojo>> activate(@PathVariable String id,@Valid @RequestBody ActivationUsersRequest activationUsersRequest,
                                                          HttpServletRequest request
    ) {
        DataResponse<UsersPojo> data = usersService.activationUsers(id,activationUsersRequest);
        return ResponseEntity.ok().body(data);
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update an existing users data"
    )
    public ResponseEntity<DataResponse<UsersPojo>> update(
            @RequestParam String id,
            @Valid @RequestBody UsersPojo usersPojo
    ) {
        DataResponse<UsersPojo> data = usersService.updateUsers(id, usersPojo);
        return ResponseEntity.ok().body(data);
    }


    @DeleteMapping
    @Operation(
            summary = "Delete users"
    )
    public ResponseEntity<DefaultResponse> delete(
            @RequestParam String id
    ) {
        DefaultResponse response = usersService.delete(id);
        return ResponseEntity.ok().body(response);
    }

}
