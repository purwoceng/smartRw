package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.VotingPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface VotingMapper {
    VotingPojo findById(String id);

    List<VotingPojo> findAll(@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    void insert(VotingPojo votingPojo);

    Optional<VotingPojo> findByUserIdandTimeVoteId(String userId,String timeVoteId);

}
