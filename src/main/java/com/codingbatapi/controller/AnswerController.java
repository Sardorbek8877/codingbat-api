package com.codingbatapi.controller;

import com.codingbatapi.entity.Answer;
import com.codingbatapi.payload.AnswerDto;
import com.codingbatapi.payload.ApiResponse;
import com.codingbatapi.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    /**
     * Get Answers
     * @return List<Answer>
     */
    @GetMapping
    public ResponseEntity<List<Answer>> getAnswers(){
        List<Answer> answerList = answerService.getAnswers();
        return ResponseEntity.ok(answerList);
    }

    /**
     * Get Answer bBy id
     * @param id
     * @return Answer
     */
    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable Integer id){
        Answer answerById = answerService.getAnswerById(id);
        return ResponseEntity.ok(answerById);
    }

    /**
     * ADD Answer
     * @param answerDto
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addAnswer(@RequestBody AnswerDto answerDto){
        ApiResponse apiResponse = answerService.addAnswer(answerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * EDIt Answer
     * @param answerDto
     * @param id
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editAnswer(@Valid @RequestBody AnswerDto answerDto, @PathVariable Integer id){
        ApiResponse apiResponse = answerService.editAnswer(answerDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Delete Answer
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAnswer(@PathVariable Integer id){
        ApiResponse apiResponse = answerService.deleteAnswer(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
