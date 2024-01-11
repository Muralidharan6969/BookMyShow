package com.example.bookmyshow.Controllers;

import com.example.bookmyshow.DTOs.BookMovieRequestDto;
import com.example.bookmyshow.Models.*;
import com.example.bookmyshow.Services.CityService;
import com.example.bookmyshow.Services.MovieService;
import com.example.bookmyshow.Services.ShowService;
import com.example.bookmyshow.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class BookingAssistanceController {
    private Scanner scn = new Scanner(System.in);
    private UserService userService;
    private CityService cityService;
    private MovieService movieService;
    private ShowService showService;

    @Autowired
    public BookingAssistanceController(UserService userService,
                                       CityService cityService,
                                       MovieService movieService,
                                       ShowService showService) {
        this.userService = userService;
        this.cityService = cityService;
        this.movieService = movieService;
        this.showService = showService;
    }

    public BookMovieRequestDto bookingMovie(Long userId){
        //Welcome message to the user
        User user = userService.getUser(userId);
        System.out.println("Welcome to the Booking Portal " + user.getUserName());

        //Requisition for City Selection
        System.out.println("Kindly select your preferred city from the below list");
        cityService.fetchCityNames().forEach(System.out::println);
        String cityName = scn.next();

        //Displaying the movies available in the selected City
        System.out.println("Following are the movies available in " + cityName);
        City city = cityService.getCity(cityName);
        cityService.fetchMoviesForCity(city.getId()).forEach(System.out::println);

        //Requisition for Movie Selection for the selected City
        System.out.println("Kindly select your preferred movie");
        String movieName = scn.next();
        Movie movie = movieService.getMovie(movieName);

        //Displaying the dates available for the selected Movie
        System.out.println("Following are the dates available for the movie" + movieName);
        List<LocalDate> showDates = showService.fetchShowDatesForMovie(movie);
        Collections.sort(showDates);
        showDates.forEach(System.out::println);

        //Requisition for Date Selection for the selected Movie
        System.out.println("Kindly select your preferred date");
        String showDate = scn.next();

        //Retrieving the shows for the selected Movie and Date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate showDateLocal = LocalDate.parse(showDate, dateFormatter);
        List<Show> shows = showService.fetchShowsForMovieAndDate(movie, showDateLocal);

        //Displaying the shows for the selected Movie and Date
        System.out.println("Please find the Theatre, Screen and Show Start " +
                "Time of the available shows for the movie " + movieName + " on " + showDate +
                "for the city " + cityName);
        showService.displayShowsForMovieAndDate(shows, cityName);

        //Requisition for Show Selection for the selected Movie and Date
        System.out.println("Kindly specify the show id for your preferred show");
        Long showId = scn.nextLong();
        Show show = showService.getShow(showId);

        //Displaying the seat layout for the selected Show
        System.out.println("Following is the seat layout for the show " + show.getId() +
                " on " + show.getShowDate() + " at " + show.getShowStartTime());
        showService.displaySeatLayoutForShow(show);

        //Requisition for Seat Selection for the selected Show
        System.out.println("Kindly specify your preferred seats in the format <seatId1>,<seatId2>,<seatId3>");
        String seatIds = scn.next();
        List<Long> showSeatMappingIds = showService.returnShowSeatMappingIds(seatIds);

        //Returning the BookMovieRequestDto to the Client
        BookMovieRequestDto bookMovieRequestDto = new BookMovieRequestDto();
        bookMovieRequestDto.setUserId(userId);
        bookMovieRequestDto.setShowId(showId);
        bookMovieRequestDto.setShowSeatMappingId(showSeatMappingIds);
        return bookMovieRequestDto;
    }
}
