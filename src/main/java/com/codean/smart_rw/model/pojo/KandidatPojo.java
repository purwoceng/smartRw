package com.codean.smart_rw.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class KandidatPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String kandidatId;
    private String nik;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String image;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MultipartFile multipartFile;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer totalVote;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Timestamp updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<VotingPojo> voting;
}
