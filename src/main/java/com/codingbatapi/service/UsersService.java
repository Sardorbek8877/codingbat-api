package com.codingbatapi.service;

import com.codingbatapi.entity.Users;
import com.codingbatapi.payload.ApiResponse;
import com.codingbatapi.payload.UsersDto;
import com.codingbatapi.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    /**
     * Get Users
     * @return List<Users>
     */
    public List<Users> getUsers(){
        return usersRepository.findAll();
    }

    /**
     * Get Users by id
     * @param id
     * @return Users
     */
    public Users getUsersById(Integer id){
        Optional<Users> optionalUsers = usersRepository.findById(id);
        return optionalUsers.orElse(null);
    }

    /**
     * Add Users
     * @param usersDto
     * @return ApiResponse
     */
    public ApiResponse addUsers(UsersDto usersDto){
        boolean existsByEmail = usersRepository.existsByEmail(usersDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("Email already exist", false);

        Users users = new Users();
        users.setEmail(usersDto.getEmail());
        users.setPassword(usersDto.getPassword());
        usersRepository.save(users);
        return  new ApiResponse("User added", true);
    }

    /**
     * EDIT Users
     * @param usersDto
     * @param id
     * @return ApiResponse
     */
    public ApiResponse editUsers(UsersDto usersDto, Integer id){
        boolean existsByEmail = usersRepository.existsByEmail(usersDto.getEmail());
        if (existsByEmail)
            return new ApiResponse("Email already exist", false);

        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isEmpty())
            return new ApiResponse("User not found", false);
        Users users = optionalUsers.get();
        users.setEmail(usersDto.getEmail());
        users.setPassword(usersDto.getPassword());
        usersRepository.save(users);
        return new ApiResponse("User edited", true);
    }

    /**
     * Delete User
     * @param id
     * @return ApiResponse
     */
    public ApiResponse deleteUsers( Integer id){
        try {
            usersRepository.deleteById(id);
            return new ApiResponse("User deleted", true);
        }
        catch (Exception e){
            return new ApiResponse("User not found", false);
        }
    }


}
