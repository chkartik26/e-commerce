package com.example.e_commerce.dto;

import lombok.Data;

@Data
public class UsersDto {
    private String firstName;
    private String lastName;

    private String email;
    private String password;

    private String role;
}
