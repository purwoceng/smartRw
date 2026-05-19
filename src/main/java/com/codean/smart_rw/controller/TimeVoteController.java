package com.codean.smart_rw.controller;

import com.codean.smart_rw.model.pojo.TimeVotePojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.DefaultResponse;
import com.codean.smart_rw.service.TimeVoteService;
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
@RequestMapping(value = "/api/smart-rw/timevote/")
@Tag(name = "Time Vote Service", description = "API Collections for Time Vote Pilihan RW")
public class TimeVoteController {
    private final TimeVoteService timeVoteService;

    @GetMapping
    @Operation(
            summary = "Get Time Vote List",
            description = "fetches all Time Vote from data source"
    )
    public ResponseEntity<DatatableResponse<TimeVotePojo>> getDatatable(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "id", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) String sortOrder
    ) {
        DatatableResponse<TimeVotePojo> list = timeVoteService.getDatatable(page, limit, sortField, sortOrder);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new Time Vote",
            description = "Create a new time vote and save it into the data source"
    )
    public ResponseEntity<DataResponse<TimeVotePojo>> create(@Valid @RequestBody TimeVotePojo timeVotePojo,
                                                          HttpServletRequest request
    ) {
        DataResponse<TimeVotePojo> data = timeVoteService.create(timeVotePojo);
        return ResponseEntity.ok().body(data);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update an existing time vote data"
    )
    public ResponseEntity<DataResponse<TimeVotePojo>> update(
            @RequestParam String id,
            @Valid @RequestBody TimeVotePojo timeVotePojo
    ) {
        DataResponse<TimeVotePojo> data = timeVoteService.update(id, timeVotePojo);
        return ResponseEntity.ok().body(data);
    }


    @DeleteMapping
    @Operation(
            summary = "Delete time vote"
    )
    public ResponseEntity<DefaultResponse> delete(
            @RequestParam String id
    ) {
        DefaultResponse response = timeVoteService.delete(id);
        return ResponseEntity.ok().body(response);
    }

}
