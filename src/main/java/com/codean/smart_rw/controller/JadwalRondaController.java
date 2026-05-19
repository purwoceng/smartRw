package com.codean.smart_rw.controller;

import com.codean.smart_rw.model.pojo.AsetPojo;
import com.codean.smart_rw.model.pojo.JadwalRondaPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;
import com.codean.smart_rw.service.JadwalRondaService;
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
@RequestMapping(value = "/api/smart-rw/jadwal-ronda/")
@Tag(name = "Jadwal Ronda Service", description = "API Collections for jadwal ronda")
public class JadwalRondaController {
    private final JadwalRondaService jadwalRondaService;

    @GetMapping
    @Operation(
            summary = "Get Jadwal Ronda List",
            description = "fetches all jadwal ronda from data source"
    )
    public ResponseEntity<DatatableResponse<JadwalRondaPojo>> getDatatable(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "id", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) String sortOrder
    ) {
        DatatableResponse<JadwalRondaPojo> list = jadwalRondaService.getDatatable(page, limit, sortField, sortOrder);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = "/findOne")
    @Operation(
            summary = "Get Detailed Jadwal Ronda",
            description = "fetch detailed jadwal ronda from data source"
    )
    public ResponseEntity<DataResponse<JadwalRondaPojo>> getById(
            @RequestParam String id
    ) {
        DataResponse<JadwalRondaPojo> data = jadwalRondaService.findOne(id);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new Jadwal Ronda",
            description = "Create a new Jadwal Ronda and save it into the data source.\n\nHari Ronda(SENIN,SELASA,RABU,KAMIS,JUMAT,SABTU,MINGGU)"
    )
    public ResponseEntity<DataResponse<JadwalRondaPojo>> create(@Valid @RequestBody JadwalRondaPojo jadwalRondaPojo,
                                                         HttpServletRequest request
    ) {
        DataResponse<JadwalRondaPojo> data = jadwalRondaService.create(jadwalRondaPojo);
        return ResponseEntity.ok().body(data);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update an existing jadwal ronda"
    )
    public ResponseEntity<DataResponse<JadwalRondaPojo>> update(
            @RequestParam String id,
            @Valid @RequestBody JadwalRondaPojo jadwalRondaPojo
    ) {
        DataResponse<JadwalRondaPojo> data = jadwalRondaService.update(id, jadwalRondaPojo);
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping
    @Operation(
            summary = "Delete Jadwal Ronda"
    )
    public ResponseEntity<DefaultResponse> delete(
            @RequestParam String id
    ) {
        DefaultResponse response = jadwalRondaService.delete(id);
        return ResponseEntity.ok().body(response);
    }
}
