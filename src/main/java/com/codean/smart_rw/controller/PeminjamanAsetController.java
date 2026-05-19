package com.codean.smart_rw.controller;

import com.codean.smart_rw.model.pojo.AsetPojo;
import com.codean.smart_rw.model.pojo.PeminjamanAsetPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;
import com.codean.smart_rw.service.PeminjamanAsetService;
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
@RequestMapping(value = "/api/smart-rw/peminjaman-aset/")
@Tag(name = "Peminjaman Aset Service", description = "API Collections for Peminjaman Aset RW")
public class PeminjamanAsetController {
    private final PeminjamanAsetService peminjamanAsetService;

    @GetMapping
    @Operation(
            summary = "Get Peminjaman Aset List",
            description = "fetches all peminjaman aset from data source"
    )
    public ResponseEntity<DatatableResponse<PeminjamanAsetPojo>> getDatatable(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "id", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) String sortOrder
    ) {
        DatatableResponse<PeminjamanAsetPojo> list = peminjamanAsetService.getDatatable(page, limit, sortField, sortOrder);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = "/findOne")
    @Operation(
            summary = "Get Detailed Peminjaman Aset",
            description = "fetch detailed peminjaman aset from data source"
    )
    public ResponseEntity<DataResponse<PeminjamanAsetPojo>> getById(
            @RequestParam String id
    ) {
        DataResponse<PeminjamanAsetPojo> data = peminjamanAsetService.findOne(id);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new Peminjaman Aset",
            description = "Create a new aset and save it into the data source.\n\nStatus  Peminjaman Aset(DIAJUKAN,DISETUJUI,DIBATALKAN)"
    )
    public ResponseEntity<DataResponse<PeminjamanAsetPojo>> create(@Valid @RequestBody PeminjamanAsetPojo peminjamanAsetPojo,
                                                         HttpServletRequest request
    ) {
        DataResponse<PeminjamanAsetPojo> data = peminjamanAsetService.create(peminjamanAsetPojo);
        return ResponseEntity.ok().body(data);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update an existing peminjaman aset"
    )
    public ResponseEntity<DataResponse<PeminjamanAsetPojo>> update(
            @RequestParam String id,
            @Valid @RequestBody PeminjamanAsetPojo peminjamanAsetPojo
    ) {
        DataResponse<PeminjamanAsetPojo> data = peminjamanAsetService.update(id, peminjamanAsetPojo);
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping
    @Operation(
            summary = "Delete peminjaman aset"
    )
    public ResponseEntity<DefaultResponse> delete(
            @RequestParam String id
    ) {
        DefaultResponse response = peminjamanAsetService.delete(id);
        return ResponseEntity.ok().body(response);
    }

}
