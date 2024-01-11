package com.example.bookmyshow;

import com.example.bookmyshow.Controllers.AdminController;
import com.example.bookmyshow.Controllers.BookingAssistanceController;
import com.example.bookmyshow.Controllers.TicketController;
import com.example.bookmyshow.Controllers.UserController;
import com.example.bookmyshow.DTOs.BookMovieRequestDto;
import com.example.bookmyshow.DTOs.BookMovieResponseDto;
import com.example.bookmyshow.DTOs.SignUpRequestDto;
import com.example.bookmyshow.DTOs.SignUpResponseDto;
import com.example.bookmyshow.Models.ENUMS.SignUpResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class BookMyShowApplication implements CommandLineRunner {
    private Scanner scn = new Scanner(System.in);
    private AdminController adminController;
    private UserController userController;
    private BookingAssistanceController bookingAssistanceController;
    private TicketController ticketController;

    @Autowired
    public BookMyShowApplication(AdminController adminController, UserController userController,
                                 BookingAssistanceController bookingAssistanceController,
                                 TicketController ticketController){
        this.adminController = adminController;
        this.userController = userController;
        this.bookingAssistanceController = bookingAssistanceController;
        this.ticketController = ticketController;
    }

    public static void main(String[] args) {

        SpringApplication.run(BookMyShowApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to BookMyShow Application");
        System.out.println("Please confirm whether you are an Admin or a User. " +
                "Enter 1 for Admin and 2 for User");
        int choice = scn.nextInt();
        if (choice == 1) {
            adminController.adminMenu();
        } else if (choice == 2) {
            System.out.println("Please enter your email id");
            String email = scn.next();
            SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
            signUpRequestDto.setEmail(email);
            SignUpResponseDto signUpResponseDto = userController.signUp(signUpRequestDto);
            if (signUpResponseDto.getSignUpResponseStatus().equals(SignUpResponseStatus.SUCCESSFUL)) {
                System.out.println("Your user id is " + signUpResponseDto.getUserId());
                BookMovieRequestDto bookMovieRequestDto =
                        bookingAssistanceController.bookingMovie(signUpResponseDto.getUserId());
                BookMovieResponseDto bookMovieResponseDto =
                        ticketController.bookMovie(bookMovieRequestDto);
            } else {
                System.out.println("Sign up unsuccessful");
            }
        } else {
            System.out.println("Invalid choice");
            System.out.println("Thank you for using BookMyShow Application");
            System.exit(0);
        }
    }
}
