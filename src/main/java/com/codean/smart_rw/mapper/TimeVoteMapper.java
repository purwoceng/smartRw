package com.codean.smart_rw.mapper;

import com.codean.smart_rw.model.pojo.TimeVotePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TimeVoteMapper {
    TimeVotePojo findById(String id);

    List<TimeVotePojo> findAll (@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    void insert(TimeVotePojo timeVotePojo);

    void update(TimeVotePojo timeVotePojo);

    void delete(String id);
}
