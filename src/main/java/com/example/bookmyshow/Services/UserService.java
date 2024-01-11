package com.example.bookmyshow.Services;

import com.example.bookmyshow.Models.User;
import com.example.bookmyshow.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Scanner;

@Service
public class UserService {
    private Scanner scn = new Scanner(System.in);
    private UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User signUp(String email){
        Optional<User> userOptional = userRepository.findByEmailId(email);
        if(userOptional.isPresent()){
            System.out.println("User already exists");
            System.out.println("Kindly enter your password to login");
            String password = scn.next();
            return login(email, password);
        }
        System.out.println("User does not exist. Kindly enter your details");
        System.out.println("Kindly enter your name");
        String name = scn.next();
        System.out.println("Kindly enter your phone number");
        String phoneNumber = scn.next();
        User user = new User();
        user.setUserName(name);
        user.setPhoneNo(phoneNumber);
        user.setEmailId(email);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println("Kindly enter your sign-up password");
        String password = scn.next();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User temp = userRepository.save(user);
        System.out.println("User Sign-Up Successful. Kindly login to continue");
        return login(email, password);
    }

    private User login(String email, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findByEmailId(email).get();
        if(bCryptPasswordEncoder.matches(password, user.getPassword())){
            System.out.println("Login Successful");
            return user;
        }
        return null;
    }

    public User getUser(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return userOptional.get();
    }
}
