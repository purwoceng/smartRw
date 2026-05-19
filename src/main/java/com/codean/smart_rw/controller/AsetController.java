package com.codean.smart_rw.controller;

import com.codean.smart_rw.model.pojo.AsetPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;
import com.codean.smart_rw.service.AsetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/smart-rw/aset/")
@Tag(name = "Aset Service", description = "API Collections for aset")
public class AsetController {
    private final AsetService asetService;

    @GetMapping
    @Operation(
            summary = "Get Aset List",
            description = "fetches all aset from data source"
    )
    public ResponseEntity<DatatableResponse<AsetPojo>> getDatatable(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "id", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) String sortOrder
    ) {
        DatatableResponse<AsetPojo> list = asetService.getDatatable(page, limit, sortField, sortOrder);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = "/findOne")
    @Operation(
            summary = "Get Detailed Aset",
            description = "fetch detailed aset from data source"
    )
    public ResponseEntity<DataResponse<AsetPojo>> getById(
            @RequestParam String id
    ) {
        DataResponse<AsetPojo> data = asetService.findOne(id);
        return ResponseEntity.ok().body(data);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new Aset",
            description = "Create a new aset and save it into the data source.\n\nStatus Aset(TERSEDIA,DIPINJAM,RUSAK,DIPERBAIKI)"
    )
    public ResponseEntity<DataResponse<AsetPojo>> create(@Valid @RequestBody AsetPojo asetPojo,
                                                           HttpServletRequest request
    ) {
        DataResponse<AsetPojo> data = asetService.create(asetPojo);
        return ResponseEntity.ok().body(data);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update an existing aset"
    )
    public ResponseEntity<DataResponse<AsetPojo>> update(
            @RequestParam String id,
            @Valid @RequestBody AsetPojo asetPojo
    ) {
        DataResponse<AsetPojo> data = asetService.update(id, asetPojo);
        return ResponseEntity.ok().body(data);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    @Operation(
            summary = "Delete aset"
    )
    public ResponseEntity<DefaultResponse> delete(
            @RequestParam String id
    ) {
        DefaultResponse response = asetService.delete(id);
        return ResponseEntity.ok().body(response);
    }

}
