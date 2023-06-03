package com.codingbatapi.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {

    @NotNull(message = "Email does not to be empty")
    private String email;

    @NotNull(message = "Password does not to be empty")
    private String password;
}
