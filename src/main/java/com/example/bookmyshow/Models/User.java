package com.example.bookmyshow.Models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity (name = "USERS")
public class User extends BaseModel{
    private String userName;
    private String emailId;
    private String phoneNo;
    private String password;
}
