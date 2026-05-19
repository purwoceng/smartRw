package com.codean.smart_rw.controller;

import com.codean.smart_rw.model.pojo.KandidatPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;
import com.codean.smart_rw.service.KandidatService;
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
@RequestMapping(value = "/api/smart-rw/kandidat/")
@Tag(name = "Kandidat Service", description = "API Collections for Kandidat")
public class KandidatController {
    private final KandidatService kandidatService;

    @GetMapping
    @Operation(
            summary = "Get Kandidat List",
            description = "fetches all kandidat from data source"
    )
    public ResponseEntity<DatatableResponse<KandidatPojo>> getDatatable(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "id", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) String sortOrder
    ) {
        DatatableResponse<KandidatPojo> list = kandidatService.getDatatable(page, limit, sortField, sortOrder);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = "/findOne")
    @Operation(
            summary = "Get Detailed Kandidat Ketua RW",
            description = "fetch detailed kandidat from data source"
    )
    public ResponseEntity<DataResponse<KandidatPojo>> getById(
            @RequestParam String id
    ) {
        DataResponse<KandidatPojo> data = kandidatService.findOne(id);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping(consumes = "multipart/form-data")
    @Operation(
            summary = "Create a new kandidat",
            description = "Create a new kandidat and save it into the data source"
    )
    public ResponseEntity<DataResponse<KandidatPojo>> create(@Valid @ModelAttribute KandidatPojo kandidatPojo,
                                                          HttpServletRequest request
    ) throws IOException {
        DataResponse<KandidatPojo> data = kandidatService.create(kandidatPojo);
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping
    @Operation(summary = "Delete a kandidat data")
    public ResponseEntity<DefaultResponse> delete(@RequestParam String id){
        DefaultResponse response = kandidatService.delete(id);
        return ResponseEntity.ok().body(response);
    }
}
