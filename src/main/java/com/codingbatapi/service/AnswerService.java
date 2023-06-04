package com.codingbatapi.service;

import com.codingbatapi.entity.Answer;
import com.codingbatapi.entity.Task;
import com.codingbatapi.entity.Users;
import com.codingbatapi.payload.AnswerDto;
import com.codingbatapi.payload.ApiResponse;
import com.codingbatapi.repository.AnswerRepository;
import com.codingbatapi.repository.TaskRepository;
import com.codingbatapi.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UsersRepository usersRepository;

    /**
     * Get Answers
     * @return List<Answer>
     */
    public List<Answer> getAnswers(){
        return answerRepository.findAll();
    }

    /**
     * Get Answer bBy id
     * @param id
     * @return Answer
     */
    public Answer getAnswerById(Integer id){
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        return optionalAnswer.orElse(null);
    }

    /**
     * ADD Answer
     * @param answerDto
     * @return ApiResponse
     */
    public ApiResponse addAnswer(AnswerDto answerDto){
        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        if (optionalTask.isEmpty())
            return new ApiResponse("Task not found", false);
        Task task = optionalTask.get();

        Optional<Users> optionalUsers = usersRepository.findById(answerDto.getUserId());
        if (optionalUsers.isEmpty())
            return new ApiResponse("User not found", false);
        Users user = optionalUsers.get();

        Answer answer = new Answer();
        answer.setText(answerDto.getText());
        answer.set_correct(answerDto.is_correct());
        answer.setUser(user);
        answer.setTask(task);


        answerRepository.save(answer);
        return new ApiResponse("Answer added", true);
    }

    /**
     * EDIt Answer
     * @param answerDto
     * @param id
     * @return ApiResponse
     */
    public ApiResponse editAnswer(AnswerDto answerDto, Integer id){

        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isEmpty())
            return new ApiResponse("Answer not found", false);
        Answer answer = optionalAnswer.get();

        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        if (optionalTask.isEmpty())
            return new ApiResponse("Task not found", false);
        Task task = optionalTask.get();

        Optional<Users> optionalUsers = usersRepository.findById(answerDto.getUserId());
        if (optionalUsers.isEmpty())
            return new ApiResponse("User not found", false);
        Users users = optionalUsers.get();

        answer.setTask(task);
        answer.setUser(users);
        answer.setText(answerDto.getText());
        answer.set_correct(answerDto.is_correct());
        answerRepository.save(answer);
        return new ApiResponse("Answer edited", true);
    }

    /**
     * Delete Answer
     * @param id
     * @return ApiResponse
     */
    public ApiResponse deleteAnswer(Integer id){
        try {
            answerRepository.deleteById(id);
            return new ApiResponse("Answer deleted", true);
        }
        catch (Exception e){
            return new ApiResponse("Answer not found", false);
        }
    }
}
