package com.codingbatapi.controller;

import com.codingbatapi.entity.Category;
import com.codingbatapi.payload.ApiResponse;
import com.codingbatapi.payload.CategoryDto;
import com.codingbatapi.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * Get Categories
     * @return List<Category>
     */
    @GetMapping
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Get Category by id
     * @param id
     * @return Category
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategories(@PathVariable Integer id){
        Category category = categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }

    /**
     * Add Category
     * @param categoryDto
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addCategories(@RequestBody CategoryDto categoryDto){
        ApiResponse apiResponse = categoryService.addCategory(categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Edit Category
     * @param categoryDto
     * @param id
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editCategories(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer id){
        ApiResponse editCategory = categoryService.editCategory(categoryDto, id);
        return ResponseEntity.status(editCategory.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(editCategory);
    }

    /**
     * Delete Category
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){
        ApiResponse apiResponse = categoryService.deleteCategory(id);
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
