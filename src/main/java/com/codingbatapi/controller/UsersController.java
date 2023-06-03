package com.codingbatapi.controller;

import com.codingbatapi.entity.Users;
import com.codingbatapi.payload.ApiResponse;
import com.codingbatapi.payload.UsersDto;
import com.codingbatapi.service.UsersService;
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
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    /**
     * Get Users
     * @return List<Users>
     */
    @GetMapping
    public ResponseEntity<List<Users>> getUsers(){
        List<Users> users = usersService.getUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Get Users by id
     * @param id
     * @return Users
     */
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUsersById(@PathVariable Integer id){
        Users users = usersService.getUsersById(id);
        return ResponseEntity.ok(users);
    }

    /**
     * Add Users
     * @param usersDto
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addUsers(@Valid @RequestBody UsersDto usersDto){
        ApiResponse apiResponse = usersService.addUsers(usersDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * EDIT Users
     * @param usersDto
     * @param id
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editUsers(@Valid @RequestBody UsersDto usersDto, @PathVariable Integer id){
        ApiResponse apiResponse = usersService.editUsers(usersDto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Delete User
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id){
        ApiResponse apiResponse = usersService.deleteUsers(id);
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
