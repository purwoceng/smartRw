package com.codean.smart_rw.model.dto;

import com.codean.smart_rw.model.pojo.RolesPojo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ActivationUsersRequest {
    @Email(message = "format email harus benar")
    @NotBlank(message = "email tidak boleh kosong")
    private String email;
    @NotBlank(message = "username tidak boleh kosong")
    private String userName;
    @NotBlank(message = "password tidak boleh kosong")
    private String password;

    private List<String> roleId;
}
