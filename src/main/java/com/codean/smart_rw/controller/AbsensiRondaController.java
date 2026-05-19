package com.codean.smart_rw.controller;

import com.codean.smart_rw.model.pojo.AbsensiRondaPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;
import com.codean.smart_rw.service.AbsensiRondaService;
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
@RequestMapping(value = "/api/smart-rw/absensi")
@Tag(name = "Absensi Ronda Service", description = "API Collections for absensi ronda")
public class AbsensiRondaController {
    private final AbsensiRondaService absensiRondaService;

    @GetMapping
    @Operation(
            summary = "Get Absensi List",
            description = "fetches all absensi from data source"
    )
    public ResponseEntity<DatatableResponse<AbsensiRondaPojo>> getDatatable(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "id", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) String sortOrder
    ) {
        DatatableResponse<AbsensiRondaPojo> list = absensiRondaService.getDatatable(page, limit, sortField, sortOrder);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = "/findOne")
    @Operation(
            summary = "Get Detailed absensi",
            description = "fetch detailed absensi from data source"
    )
    public ResponseEntity<DataResponse<AbsensiRondaPojo>> getById(
            @RequestParam String id
    ) {
        DataResponse<AbsensiRondaPojo> data = absensiRondaService.findOne(id);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new absensi ronda",
            description = "Create a new aset and save it into the data source.\n\nStatus Absensi (HADIR,IZIN,TIDAK_HADIR)"
    )
    public ResponseEntity<DataResponse<AbsensiRondaPojo>> create(@Valid @RequestBody AbsensiRondaPojo absensiRondaPojo,
                                                         HttpServletRequest request
    ) {
        DataResponse<AbsensiRondaPojo> data = absensiRondaService.create(absensiRondaPojo);
        return ResponseEntity.ok().body(data);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update an existing absensi ronda"
    )
    public ResponseEntity<DataResponse<AbsensiRondaPojo>> update(
            @RequestParam String id,
            @Valid @RequestBody AbsensiRondaPojo absensiRondaPojo
    ) {
        DataResponse<AbsensiRondaPojo> data = absensiRondaService.update(id, absensiRondaPojo);
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping
    @Operation(
            summary = "Delete absensi ronda"
    )
    public ResponseEntity<DefaultResponse> delete(
            @RequestParam String id
    ) {
        DefaultResponse response = absensiRondaService.delete(id);
        return ResponseEntity.ok().body(response);
    }

}
