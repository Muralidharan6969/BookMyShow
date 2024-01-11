package com.example.bookmyshow.Controllers;

import com.example.bookmyshow.Models.*;

import com.example.bookmyshow.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.util.Scanner;

@Controller
public class AdminController {
    private Scanner scn = new Scanner(System.in);

    private MovieService movieService;
    private CityService cityService;
    private TheatreService theatreService;
    private ScreenService screenService;
    private ShowService showService;

    @Autowired
    public AdminController(MovieService movieService,
                           CityService cityService,
                           TheatreService theatreService,
                           ScreenService screenService,
                           ShowService showService) {
        this.movieService = movieService;
        this.cityService = cityService;
        this.theatreService = theatreService;
        this.screenService = screenService;
        this.showService = showService;
    }

    public void adminMenu() {
        System.out.println("Welcome to the Admin Portal of BookMyShow");
        while (true) {
            System.out.println("What would you like to do?");
            System.out.println("1. Add a new Movie");
            System.out.println("2. Add a new City");
            System.out.println("3. Add a new Theatre");
            System.out.println("4. Add a new Screen");
            System.out.println("5. Add a new Show");
            System.out.println("6. Exit");
            int choice = scn.nextInt();
            if (choice == 1) {
                addMovie();
            } else if (choice == 2) {
                addCity();
            } else if (choice == 3) {
                addTheatre();
            } else if (choice == 4) {
                addScreen();
            } else if (choice == 5) {
                addShow();
            } else if (choice == 6) {
                System.out.println("Thank you for using BookMyShow Application");
                System.exit(0);
            } else {
                System.out.println("Invalid choice");
            }
            System.out.println("Do you want to continue updating the database? " +
                    "Enter 1 for yes and 2 for no");
            int continueChoice = scn.nextInt();
            if (continueChoice == 2) {
                System.out.println("Thank you for using BookMyShow Application");
                System.exit(0);
            }
        }
    }

    private void addShow() {
        Show show = showService.addShow();
        System.out.println("Updated the database with the show for the movie " +
                show.getMovie().getMovieName() + " successfully");
    }

    private void addScreen() {
        Screen screen = screenService.addScreen();
        System.out.println("Updated the database with the screen " +
                screen.getScreenName() + " for the theatre " + screen.getTheatre().getTheatreName() +
                " successfully");
    }

    private void addTheatre() {
        Theatre theatre = theatreService.addTheatre();
        System.out.println("Updated the database with the theatre " + theatre.getTheatreName() + " successfully");
    }

    private void addCity() {
        City city = cityService.addCity();
        System.out.println("Updated the database with the city " + city.getCityName() + " successfully");
    }

    private void addMovie() {
        Movie movie = movieService.addMovie();
        System.out.println("Updated the database with the movie " + movie.getMovieName() + " successfully");
    }

}
