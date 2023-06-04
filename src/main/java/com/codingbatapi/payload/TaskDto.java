package com.codingbatapi.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @NotNull(message = "Name does not to be empty")
    private String name;

    private String text;

    private String solution;

    private String hint;

    private String method;

    private boolean has_star;

    private Integer languageId;
}
