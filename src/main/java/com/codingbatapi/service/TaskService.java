package com.codingbatapi.service;

import com.codingbatapi.entity.Language;
import com.codingbatapi.entity.Task;
import com.codingbatapi.payload.ApiResponse;
import com.codingbatapi.payload.TaskDto;
import com.codingbatapi.repository.LanguageRepository;
import com.codingbatapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    LanguageRepository languageRepository;

    /**
     * Get Tasks
     * @return List<Task>
     */
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    /**
     * Get Task By id
     * @param id
     * @return Task
     */
    public Task getTaskById(Integer id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElse(null);
    }

    /**
     * ADD Task
     * @param taskDto
     * @return ApiResponse
     */
    public ApiResponse addTask(TaskDto taskDto){
        boolean existsByName = taskRepository.existsByName(taskDto.getName());
        if (existsByName)
            return new ApiResponse("Name already exist", false);

        Optional<Language> optionalLanguage = languageRepository.findById(taskDto.getLanguageId());
        if (optionalLanguage.isEmpty())
            return new ApiResponse("Language not found", false);
        Language language = optionalLanguage.get();

        Task task = new Task();
        task.setHint(taskDto.getHint());
        task.setName(taskDto.getName());
        task.setMethod(taskDto.getMethod());
        task.setLanguage(language);
        task.setText(taskDto.getText());
        task.setSolution(taskDto.getSolution());

        taskRepository.save(task);
        return new ApiResponse("Task added", true);
    }

    /**
     * EDIT Task
     * @param taskDto
     * @param id
     * @return ApiResponse
     */
    public ApiResponse editTask(TaskDto taskDto, Integer id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty())
            return new ApiResponse("Task not found", false);
        Task task = optionalTask.get();

        boolean existsByName = taskRepository.existsByName(taskDto.getName());
        if (existsByName)
            return new ApiResponse("Name already exist", false);

        Optional<Language> optionalLanguage = languageRepository.findById(taskDto.getLanguageId());
        if (optionalLanguage.isEmpty())
            return new ApiResponse("Language not found", false);
        Language language = optionalLanguage.get();

        task.setHint(taskDto.getHint());
        task.setName(taskDto.getName());
        task.setMethod(taskDto.getMethod());
        task.setLanguage(language);
        task.setText(taskDto.getText());
        task.setSolution(taskDto.getSolution());

        taskRepository.save(task);
        return new ApiResponse("Task edited", true);
    }

    /**
     * Delete Task
     * @param id
     * @return ApiResponse
     */
    public ApiResponse deleteTask(Integer id){
        try {
            taskRepository.deleteById(id);
            return new ApiResponse("Task deleted", true);
        }
        catch (Exception e){
            return new ApiResponse("Task not found", false);
        }
    }
}
