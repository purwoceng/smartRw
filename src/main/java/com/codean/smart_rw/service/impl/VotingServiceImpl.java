package com.codean.smart_rw.service.impl;

import com.codean.smart_rw.config.CustomUsersDetails;
import com.codean.smart_rw.exception.custom.NotFoundException;
import com.codean.smart_rw.mapper.KandidatMapper;
import com.codean.smart_rw.mapper.TimeVoteMapper;
import com.codean.smart_rw.mapper.VotingMapper;
import com.codean.smart_rw.model.pojo.KandidatPojo;
import com.codean.smart_rw.model.pojo.TimeVotePojo;
import com.codean.smart_rw.model.pojo.VotingPojo;
import com.codean.smart_rw.model.response.DataResponse;
import com.codean.smart_rw.model.response.DatatableResponse;
import com.codean.smart_rw.model.response.PageDataResponse;
import com.codean.smart_rw.model.response.ResponseMessage;
import com.codean.smart_rw.service.VotingService;
import com.codean.smart_rw.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VotingServiceImpl implements VotingService {
    private final VotingMapper votingMapper;

    private final KandidatMapper kandidatMapper;

    private final TimeVoteMapper timeVoteMapper;

    private static final String SUCCESS = "Success";

    private static final Logger log = LogManager.getLogger(VotingServiceImpl.class);

    @Override
    public DatatableResponse<VotingPojo> getDatatable(int page, int limit, String sortField, String sortOrder) {
        try {
            Map<String, String> allowedOrder = new HashMap<>();
            allowedOrder.put("id", "vote_id");
            String sortColumn = "vote_id";
            if (allowedOrder.containsKey(sortField)) {
                sortColumn = allowedOrder.getOrDefault(sortField, null);
            }
            String sortType = Objects.equals(sortOrder, "DESC") ? "DESC" : "ASC";

            int offset = (page - 1) * limit;
            List<VotingPojo> pageResult = votingMapper.findAll(offset, limit, sortColumn, sortType);

            PageDataResponse<VotingPojo> data = new PageDataResponse<>(page, limit, pageResult.size(), pageResult);

            return new DatatableResponse<>(
                    SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), data
            );
        } catch (Exception e) {
            log.error("Error when get datatable voting .", e);
            throw e;
        }
    }

    @Override
    public DataResponse<VotingPojo> create(VotingPojo votingPojo) {
        try {
            KandidatPojo kandidat = kandidatMapper.findById(votingPojo.getKandidatId());

            if(kandidat == null){
                throw new NotFoundException("kandidat tidak terdaftar");
            }

            Optional<VotingPojo> optionalVotingPojo = votingMapper.findByUserIdandTimeVoteId(votingPojo.getUserId(), votingPojo.getTimeVoteId());

            if (optionalVotingPojo.isPresent()){
                throw new RuntimeException("Data user telah melakukan voting");
            }

            TimeVotePojo timeVotePojo = timeVoteMapper.findById(votingPojo.getTimeVoteId());

            if (timeVotePojo == null) {
                throw new NotFoundException("Data waktu voting tidak ditemukan");
            }

            if (LocalDateTime.now().isBefore(timeVotePojo.getJamMulai())){
                throw new IllegalArgumentException("Voting belum di mulai harap tunggu");
            }

            if (LocalDateTime.now().isAfter(timeVotePojo.getJamSelesai())){
                throw new IllegalArgumentException("Voting telah ditutup.Mohon Maaf");
            }

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUsersDetails users = (CustomUsersDetails) auth.getPrincipal();
            String userId = users.getUserId();

            votingPojo.setCreatedAt(new DateHelper().getCurrentTimestamp());
            votingPojo.setVoteId(UUID.randomUUID().toString());
            votingPojo.setUserId(userId);

            votingMapper.insert(votingPojo);

            kandidatMapper.incrementTotalVote(votingPojo.getKandidatId());

            VotingPojo data = votingMapper.findById(votingPojo.getVoteId());
            return new DataResponse<>(SUCCESS, ResponseMessage.DATA_CREATED,  HttpStatus.OK.value(), data);
        } catch (Exception e) {
            log.error("Error when create a voting.", e);
            throw e;
        }
    }
}

