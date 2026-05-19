package com.codean.smart_rw.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class VotingPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String voteId;
    private String kandidatId;
    private String timeVoteId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp updatedAt;
}
