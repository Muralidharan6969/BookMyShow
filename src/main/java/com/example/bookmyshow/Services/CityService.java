package com.example.bookmyshow.Services;

import com.example.bookmyshow.Models.*;
import com.example.bookmyshow.Repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CityService {
    private Scanner scn = new Scanner(System.in);
    private CityRepository cityRepository;
    private BookMyShow bookMyShow;

    @Autowired
    public CityService(CityRepository cityRepository, BookMyShow bookMyShow) {
        this.cityRepository = cityRepository;
        this.bookMyShow = bookMyShow;
    }

    public City addCity(){
        System.out.println("Please enter the name of the city");
        String cityName = scn.next();
        City city = new City();
        city.setCityName(cityName);
        bookMyShow.getCities().add(city);
        return cityRepository.save(city);
    }

    public List<String> fetchCityNames(){
        List<String> cityNames = new ArrayList<>();
        cityRepository.findAll().forEach(city -> cityNames.add(city.getCityName()));
        return cityNames;
    }

    public City getCity(String cityName){
        Optional<City> cityOptional = cityRepository.findByCityName(cityName);
        if(cityOptional.isEmpty()){
            throw new RuntimeException("City not found");
        }
        return cityOptional.get();
    }

    public List<String> fetchMoviesForCity(Long cityId){
        List<String> movieNames = new ArrayList<>();
        Optional<City> cityOptional = cityRepository.findById(cityId);
        City city = cityOptional.get();
        for(Theatre t : city.getTheatreList()){
            for(Screen s : t.getScreenList()){
                for(Show show : s.getShowList()){
                    if(!movieNames.contains(show.getMovie().getMovieName())){
                        movieNames.add(show.getMovie().getMovieName());
                    }
                }
            }
        }
        return movieNames;
    }
}
