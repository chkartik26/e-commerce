package com.example.e_commerce.service.user;

import com.example.e_commerce.dto.UsersDto;
import com.example.e_commerce.exception.AlreadyExistsException;
import com.example.e_commerce.model.Users;
import com.example.e_commerce.repository.UserRepo;
import com.example.e_commerce.security.jwt.JWTService;
import com.example.e_commerce.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    @Autowired
    CartService cartService;
    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @Override
    public Users getUserById(int userId) {
        return userRepo.findById(userId).orElseThrow();
    }

    @Override
    public Users createUser(UsersDto userDto) {
        if(userRepo.existsByEmail(userDto.getEmail())){
            throw new AlreadyExistsException("User already exist with this email");
        }
        Users user=new Users();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        Users temp=userRepo.save(user);
        cartService.initializeNewCart(temp);
        return temp;
    }

    @Override
    public Users updateUser(UsersDto userDto, int userId) {
        Users user=userRepo.findById(userId).orElseThrow();
        if(userDto.getFirstName()!=null){
            user.setFirstName(userDto.getFirstName());
        }
        if(userDto.getLastName()!=null){
            user.setLastName(userDto.getLastName());
        }
        return user;
    }

    @Override
    public void deleteUser(int userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public String verify(UsersDto userDto){
        Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(),userDto.getPassword()));
        if(authentication.isAuthenticated())
            return jwtService.generateToken(userDto.getEmail());
        return "fail";
    }
}
