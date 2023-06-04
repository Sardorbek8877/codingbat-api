package com.codingbatapi.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    @NotNull(message = "Name does not to be empty")
    private String name;

    private String description;

    private List<Integer> languageId;
}
