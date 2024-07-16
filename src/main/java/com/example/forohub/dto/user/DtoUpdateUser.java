package com.example.forohub.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DtoUpdateUser(@NotBlank String username,
                            @Email String email,
                            @NotBlank String password,
                            @NotBlank Integer typeOfProfile) {
}