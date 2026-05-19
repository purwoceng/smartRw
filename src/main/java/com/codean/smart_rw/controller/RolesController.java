package com.codean.smart_rw.controller;

import com.codean.smart_rw.model.pojo.RolesPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;
import com.codean.smart_rw.service.RolesService;
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
@RequestMapping(value = "/api/smart-rw/roles/")
@Tag(name = "Roles Service", description = "API Collections for Roles")
public class RolesController {
    private final RolesService rolesService;

    @GetMapping
    @Operation(
            summary = "Get Roles List",
            description = "fetches all roles from data source"
    )
    public ResponseEntity<DatatableResponse<RolesPojo>> getDatatable(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "id", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) String sortOrder
    ) {
        DatatableResponse<RolesPojo> list = rolesService.getDatatable(page, limit, sortField, sortOrder);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new roles",
            description = "Create a new roles and save it into the data source"
    )
    public ResponseEntity<DataResponse<RolesPojo>> create(@Valid @RequestBody RolesPojo rolesPojo,
                                                          HttpServletRequest request
    ) {
        DataResponse<RolesPojo> data = rolesService.create(rolesPojo);
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping
    @Operation(
            summary = "Delete roles"
    )
    public ResponseEntity<DefaultResponse> delete(
            @RequestParam String id
    ) {
        DefaultResponse response = rolesService.delete(id);
        return ResponseEntity.ok().body(response);
    }

}
