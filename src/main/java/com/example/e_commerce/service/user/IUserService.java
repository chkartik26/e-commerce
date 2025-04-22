package com.example.e_commerce.service.user;

import com.example.e_commerce.dto.UsersDto;
import com.example.e_commerce.model.Users;

public interface IUserService {

    Users getUserById(int userId);
    Users createUser(UsersDto userDto);
    Users updateUser(UsersDto userDto, int userId);
    void deleteUser(int userId);
    String verify(UsersDto userDto);
}
