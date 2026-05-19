package com.codean.smart_rw.controller;

import com.codean.smart_rw.model.pojo.KasJimpitanPojo;
import com.codean.smart_rw.model.pojo.TotalSaldoKasPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;
import com.codean.smart_rw.service.KasJimpitanService;
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
@RequestMapping(value = "/api/smart-rw/kas/")
@Tag(name = "Kas Jimpitan Service", description = "API Collections for kas jimpitan")
public class KasJimpitanController {
    private final KasJimpitanService kasJimpitanService;

    @GetMapping
    @Operation(
            summary = "Get Kas Jimpitan List",
            description = "fetches all kas jimpitan from data source"
    )
    public ResponseEntity<DatatableResponse<KasJimpitanPojo>> getDatatable(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "created_at", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) String sortOrder
    ) {
        DatatableResponse<KasJimpitanPojo> list = kasJimpitanService.getDatatable(page, limit, sortField, sortOrder);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = "/findOne")
    @Operation(
            summary = "Get Detailed Kas Jimpitan",
            description = "fetch detailed kas jimpitan from data source"
    )
    public ResponseEntity<DataResponse<KasJimpitanPojo>> getById(
            @RequestParam String id
    ) {
        DataResponse<KasJimpitanPojo> data = kasJimpitanService.findOne(id);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping(path = "/totalSaldo")
    @Operation(
            summary = "Get Detailed Total Kas Jimpitan",
            description = "fetch detailed kas jimpitan from data source"
    )
    public ResponseEntity<DataResponse<TotalSaldoKasPojo>> getTotalSaldo() {
        DataResponse<TotalSaldoKasPojo> data = kasJimpitanService.findTotalSaldo();
        return ResponseEntity.ok().body(data);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new Kas Jimpitan",
            description = "Create a new aset and save it into the data source.\n\nTipe Kas Jimpitan(MASUK,KELUAR)"
    )
    public ResponseEntity<DataResponse<KasJimpitanPojo>> create(@Valid @RequestBody KasJimpitanPojo kasJimpitanPojo,
                                                         HttpServletRequest request
    ) {
        DataResponse<KasJimpitanPojo> data = kasJimpitanService.create(kasJimpitanPojo);
        return ResponseEntity.ok().body(data);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update an existing Kas Jimpitan"
    )
    public ResponseEntity<DataResponse<KasJimpitanPojo>> update(
            @RequestParam String id,
            @Valid @RequestBody KasJimpitanPojo kasJimpitanPojo
    ) {
        DataResponse<KasJimpitanPojo> data = kasJimpitanService.update(id, kasJimpitanPojo);
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping
    @Operation(
            summary = "Delete Kas Jimpitan"
    )
    public ResponseEntity<DefaultResponse> delete(
            @RequestParam String id
    ) {
        DefaultResponse response = kasJimpitanService.delete(id);
        return ResponseEntity.ok().body(response);
    }

}
