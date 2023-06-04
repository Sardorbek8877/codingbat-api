package com.codingbatapi.service;

import com.codingbatapi.entity.Language;
import com.codingbatapi.payload.ApiResponse;
import com.codingbatapi.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    /**
     * GetLanguages
     * @return List<Language>
     */
    public List<Language> getLanguages(){
        return languageRepository.findAll();
    }

    /**
     * Get Language By id
     * @param id
     * @return Language
     */
    public Language getLanguageById(Integer id){
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        return optionalLanguage.orElse(null);
    }

    /**
     * Add Language
     * @param language
     * @return ApiResponse
     */
    public ApiResponse addLanguage(Language language){
        boolean existsByName = languageRepository.existsByName(language.getName());
        if (existsByName)
            return new ApiResponse("Language already exsit", false);
        languageRepository.save(language);
        return new ApiResponse("Language add", true);
    }

    /**
     * EDIt Language
     * @param language
     * @param id
     * @return ApiResponse
     */
    public ApiResponse editLanguage(Language language, Integer id){
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (optionalLanguage.isEmpty())
            return new ApiResponse("Language not found", false);
        Language editingLanguage = optionalLanguage.get();

        boolean existsByName = languageRepository.existsByName(language.getName());
        if (existsByName)
            return new ApiResponse("Language already exist", false);

        editingLanguage.setName(language.getName());

        languageRepository.save(editingLanguage);
        return new ApiResponse("Language edited", true);
    }

    /**
     * Delete Language
     * @param id
     * @return ApiResponse
     */
    public ApiResponse deleteLanguage(Integer id){
        try {
            languageRepository.deleteById(id);
            return new ApiResponse("Language deleted", true);
        }
        catch (Exception e){
            return new ApiResponse("Language not found", false);
        }
    }
}
