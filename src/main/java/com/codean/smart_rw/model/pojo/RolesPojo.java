package com.codean.smart_rw.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolesPojo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String roleId;
    @NotBlank(message = "Nama role tidak boleh kosong dan null")
    private String namaRole;
}
