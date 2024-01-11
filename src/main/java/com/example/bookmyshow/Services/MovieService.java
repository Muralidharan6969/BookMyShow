package com.example.bookmyshow.Services;

import com.example.bookmyshow.Models.ENUMS.MovieFormat;
import com.example.bookmyshow.Models.Movie;
import com.example.bookmyshow.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class MovieService {
    private Scanner scn = new Scanner(System.in);
    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(){
        System.out.println("Please enter the name of the movie");
        String movieName = scn.next();
        System.out.println("Please enter the language of the movie");
        String language = scn.next();
        Movie movie = new Movie();
        movie.setMovieName(movieName);
        movie.setLanguage(language);
        System.out.println("Please enter the format of the movie");
        System.out.println("1. 2D");
        System.out.println("2. 3D");
        System.out.println("3. IMAX");
        int formatChoice = scn.nextInt();
        if (formatChoice == 1) {
            movie.setMovieFormat(MovieFormat.TWO_D);
        } else if (formatChoice == 2) {
            movie.setMovieFormat(MovieFormat.THREE_D);
        } else if (formatChoice == 3) {
            movie.setMovieFormat(MovieFormat.IMAXDISPLAY);
        } else {
            System.out.println("Invalid choice");
        }
        return movieRepository.save(movie);
    }

    public Movie getMovie(String movieName){
        Optional<Movie> movieOptional = movieRepository.findByMovieName(movieName);
        if(movieOptional.isEmpty()){
            throw new RuntimeException("Movie not found");
        }
        return movieOptional.get();
    }
}
