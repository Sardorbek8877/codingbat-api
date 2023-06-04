package com.codingbatapi.service;

import com.codingbatapi.entity.Category;
import com.codingbatapi.entity.Language;
import com.codingbatapi.payload.ApiResponse;
import com.codingbatapi.payload.CategoryDto;
import com.codingbatapi.repository.CategoryRepository;
import com.codingbatapi.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    LanguageRepository languageRepository;

    /**
     * Get Categories
     * @return List<Category>
     */
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    /**
     * Get Category by id
     * @param id
     * @return Category
     */
    public Category getCategory(Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElse(null);
    }

    /**
     * Add Category
     * @param categoryDto
     * @return ApiResponse
     */
    public ApiResponse addCategory(CategoryDto categoryDto){
        boolean existsByName = categoryRepository.existsByName(categoryDto.getName());
        if (existsByName)
            return new ApiResponse("Category already exist", false);
        Category category = new Category();

        List<Language> languageList = new ArrayList<>();
        List<Integer> languageIdList = categoryDto.getLanguageId();
        for (Integer item : languageIdList) {
            Optional<Language> optionalLanguage = languageRepository.findById(item);
            if (optionalLanguage.isEmpty()){
                return new ApiResponse("Language not found", false);
            }
            else {
                Language language = optionalLanguage.get();
                languageList.add(language);
            }
        }

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setLanguage(languageList);
        categoryRepository.save(category);
        return new ApiResponse("Category added", true);
    }


    public ApiResponse editCategory(CategoryDto categoryDto, Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty())
            return new ApiResponse("Category not found", false);
        Category category = optionalCategory.get();

        List<Language> languageList = new ArrayList<>();
        List<Integer> languageIdList = categoryDto.getLanguageId();
        for (Integer item : languageIdList ) {
            Optional<Language> optionalLanguage = languageRepository.findById(item);
            if (optionalLanguage.isEmpty())
                return new ApiResponse("Language not found", false);
            else {
                Language language = optionalLanguage.get();
                languageList.add(language);
            }
        }

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setLanguage(languageList);
        categoryRepository.save(category);
        return new ApiResponse("Category edited", true);
    }

    /**
     * Delete Category
     * @param id
     * @return ApiResponse
     */
    public ApiResponse deleteCategory(Integer id){
        try {
            categoryRepository.deleteById(id);
            return new ApiResponse("Category deleted", true);
        }
        catch (Exception e){
            return new ApiResponse("Category not found", false);
        }
    }
}
