package com.codingbatapi.controller;

import com.codingbatapi.entity.Language;
import com.codingbatapi.payload.ApiResponse;
import com.codingbatapi.service.LanguageService;
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

@RestController("/api/languages")
public class LanguageController {

    @Autowired
    LanguageService languageService;

    /**
     * GetLanguages
     * @return List<Language>
     */
    @GetMapping
    public ResponseEntity<List<Language>> getLanguages(){
        List<Language> languages = languageService.getLanguages();
        return ResponseEntity.ok(languages);
    }

    /**
     * Get Language By id
     * @param id
     * @return Language
     */
    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable Integer id){
        Language languageById = languageService.getLanguageById(id);
        return ResponseEntity.ok(languageById);
    }

    /**
     * Add Language
     * @param language
     * @return ApiResponse
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addLanguages(@RequestBody Language language){
        ApiResponse apiResponse = languageService.addLanguage(language);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * EDIt Language
     * @param language
     * @param id
     * @return ApiResponse
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editLanguages(@Valid @RequestBody Language language, @PathVariable Integer id){
        ApiResponse apiResponse = languageService.editLanguage(language, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Delete Language
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteLanguages(@PathVariable Integer id){
        ApiResponse apiResponse = languageService.deleteLanguage(id);
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
