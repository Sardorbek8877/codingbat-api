package com.codingbatapi.payload;

import com.codingbatapi.entity.Task;
import com.codingbatapi.entity.Users;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {

    private String text;

    private Integer taskId;

    private Integer userId;

    private boolean is_correct;
}
