package com.codingbatapi.controller;

import com.codingbatapi.entity.Task;
import com.codingbatapi.payload.ApiResponse;
import com.codingbatapi.payload.TaskDto;
import com.codingbatapi.service.TaskService;
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

@RestController("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    /**
     * Get Tasks
     * @return List<Task>
     */
    @GetMapping
    public ResponseEntity<List<Task>> getTasks(){
        List<Task> tasks = taskService.getTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get Task By id
     * @param id
     * @return Task
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Integer id){
        Task taskById = taskService.getTaskById(id);
        return ResponseEntity.ok(taskById);
    }

    /**
     * ADD Task
     * @param taskDto
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addTask(@Valid @RequestBody TaskDto taskDto){
        ApiResponse apiResponse = taskService.addTask(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * EDIT Task
     * @param taskDto
     * @param id
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editTask(@Valid @RequestBody TaskDto taskDto, Integer id){
        ApiResponse apiResponse = taskService.editTask(taskDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Delete Task
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Integer id){
        ApiResponse apiResponse = taskService.deleteTask(id);
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
