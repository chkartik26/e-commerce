package com.example.e_commerce.controller;

import com.example.e_commerce.dto.UsersDto;
import com.example.e_commerce.exception.AccessDenied;
import com.example.e_commerce.exception.AlreadyExistsException;
import com.example.e_commerce.exception.ResourceNotFound;
import com.example.e_commerce.response.ApiResponse;
import com.example.e_commerce.security.user.UserPrincipal;
import com.example.e_commerce.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/userInfo/{userId}")
    public ResponseEntity<?> getUser(@PathVariable int userId){
        try{
            return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
        }
        catch (ResourceNotFound e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("No user found with this id"));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UsersDto userDto){

        try{
            return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
        }
        catch(AlreadyExistsException e){
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UsersDto userDto){
        try{
            return new ResponseEntity<>(userService.verify(userDto),HttpStatus.OK);
        }
        catch (AccessDenied e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AccessDenied("Access Denied"));
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UsersDto userDto,@RequestParam int userId){
        try{
            return new ResponseEntity<>(userService.updateUser(userDto,userId),HttpStatus.OK);
        }
        catch(ResourceNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found"));
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@RequestParam int userId){
        try{
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User could not be deleted"));
        }
    }
}
