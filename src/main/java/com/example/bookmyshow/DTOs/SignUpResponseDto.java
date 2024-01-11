package com.example.bookmyshow.DTOs;

import com.example.bookmyshow.Models.ENUMS.SignUpResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {
    private Long userId;
    private SignUpResponseStatus signUpResponseStatus;
}
