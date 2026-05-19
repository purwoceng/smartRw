package com.codean.smart_rw.controller;

import com.codean.smart_rw.model.pojo.VotingPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.service.VotingService;
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
@RequestMapping(value = "/api/smart-rw/voting/")
@Tag(name = "Voting Service", description = "API Collections for Voting")
public class VotingController {
    private final VotingService votingService;

    @GetMapping
    @Operation(
            summary = "Get Voting List",
            description = "fetches all voting from data source"
    )
    public ResponseEntity<DatatableResponse<VotingPojo>> getDatatable(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "id", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) String sortOrder
    ) {
        DatatableResponse<VotingPojo> list = votingService.getDatatable(page, limit, sortField, sortOrder);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new Voting",
            description = "Create a new voting and save it into the data source"
    )
    public ResponseEntity<DataResponse<VotingPojo>> create(@Valid @RequestBody VotingPojo votingPojo,
                                                          HttpServletRequest request
    ) {
        DataResponse<VotingPojo> data = votingService.create(votingPojo);
        return ResponseEntity.ok().body(data);
    }
}
