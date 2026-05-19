package com.codean.smart_rw.controller;

import com.codean.smart_rw.model.pojo.PengumumanPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.service.PengumumanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/smart-rw/pengumuman/")
@Tag(name = "Pengumuman Service", description = "API Collections for Pengumuman")
public class PengumumanController {
    private final PengumumanService pengumumanService;

    @GetMapping
    @Operation(
            summary = "Get Pengumuman List",
            description = "fetches all pengumuman from data source"
    )
    public ResponseEntity<DatatableResponse<PengumumanPojo>> getDatatable(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "id", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) String sortOrder
    ) {
        DatatableResponse<PengumumanPojo> list = pengumumanService.getDatatable(page, limit, sortField, sortOrder);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = "/findOne")
    @Operation(
            summary = "Get Detailed Pengumuman",
            description = "fetch detailed pengumuman from data source"
    )
    public ResponseEntity<DataResponse<PengumumanPojo>> getById(
            @RequestParam String id
    ) {
        DataResponse<PengumumanPojo> data = pengumumanService.findOne(id);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping(consumes = "multipart/form-data")
    @Operation(
            summary = "Create a new Pengumuman",
            description = "Create a new Pengumuman and save it into the data source."
    )
    public ResponseEntity<DataResponse<PengumumanPojo>> create(@Valid @ModelAttribute PengumumanPojo pengumumanPojo,
                                                         HttpServletRequest request
    )throws IOException {
        DataResponse<PengumumanPojo> data = pengumumanService.create(pengumumanPojo);
        return ResponseEntity.ok().body(data);
    }
}
