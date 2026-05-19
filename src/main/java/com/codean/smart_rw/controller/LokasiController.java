package com.codean.smart_rw.controller;

import com.codean.smart_rw.model.pojo.LokasiPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;
import com.codean.smart_rw.service.LokasiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/smart-rw/lokasi/")
@Tag(name = "Lokasi Ronda Service", description = "API Collections for lokasi ronda")
public class LokasiController {
    private final LokasiService lokasiService;

    @GetMapping
    @Operation(
            summary = "Get Lokasi Ronda List",
            description = "fetches all lokasi ronda from data source"
    )
    public ResponseEntity<DatatableResponse<LokasiPojo>> getDatatable(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "id", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) String sortOrder
    ) {
        DatatableResponse<LokasiPojo> list = lokasiService.getDatatable(page, limit, sortField, sortOrder);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = "/findOne")
    @Operation(
            summary = "Get Detailed Lokasi Ronda",
            description = "fetch detailed lokasi ronda from data source"
    )
    public ResponseEntity<DataResponse<LokasiPojo>> getById(
            @RequestParam String id
    ) {
        DataResponse<LokasiPojo> data = lokasiService.findOne(id);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new Lokasi Ronda",
            description = "Create a new lokasi ronda and save it into the data source."
    )
    public ResponseEntity<DataResponse<LokasiPojo>> create(@Valid @RequestBody LokasiPojo lokasiPojo,
                                                         HttpServletRequest request
    ) {
        DataResponse<LokasiPojo> data = lokasiService.create(lokasiPojo);
        return ResponseEntity.ok().body(data);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update an existing lokasi ronda"
    )
    public ResponseEntity<DataResponse<LokasiPojo>> update(
            @RequestParam String id,
            @Valid @RequestBody LokasiPojo lokasiPojo
    ) {
        DataResponse<LokasiPojo> data = lokasiService.update(id, lokasiPojo);
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping
    @Operation(
            summary = "Delete Lokasi Ronda"
    )
    public ResponseEntity<DefaultResponse> delete(
            @RequestParam String id
    ) {
        DefaultResponse response = lokasiService.delete(id);
        return ResponseEntity.ok().body(response);
    }

}
