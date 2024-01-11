package com.example.bookmyshow.Controllers;

import com.example.bookmyshow.DTOs.SignUpRequestDto;
import com.example.bookmyshow.DTOs.SignUpResponseDto;
import com.example.bookmyshow.Models.ENUMS.SignUpResponseStatus;
import com.example.bookmyshow.Models.User;
import com.example.bookmyshow.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;
    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto){
        SignUpResponseDto responseDto = new SignUpResponseDto();
        try{
            User user = userService.signUp(signUpRequestDto.getEmail());
            responseDto.setUserId(user.getId());
            responseDto.setSignUpResponseStatus(SignUpResponseStatus.SUCCESSFUL);
        }
        catch (Exception e){
            responseDto.setSignUpResponseStatus(SignUpResponseStatus.UNSUCCESSFUL);
        }
        return responseDto;
    }
}
