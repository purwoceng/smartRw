package com.codean.smart_rw.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUsersDTO {
    @NotBlank
    @Email(message = "format email harus benar")
    private String email;
    @NotBlank(message = "password tidak boleh kosong")
    private String password;
}
