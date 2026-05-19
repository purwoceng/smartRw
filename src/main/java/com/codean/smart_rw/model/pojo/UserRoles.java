package com.codean.smart_rw.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoles {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String userRoleId;
    private String userId;
    private String roleId;
}
