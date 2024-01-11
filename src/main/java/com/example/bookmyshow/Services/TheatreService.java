package com.example.bookmyshow.Services;

import com.example.bookmyshow.Models.City;
import com.example.bookmyshow.Models.Theatre;
import com.example.bookmyshow.Repositories.CityRepository;
import com.example.bookmyshow.Repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class TheatreService {
    private Scanner scn = new Scanner(System.in);
    private TheatreRepository theatreRepository;
    private CityRepository cityRepository;

    @Autowired
    public TheatreService(TheatreRepository theatreRepository, CityRepository cityRepository) {
        this.theatreRepository = theatreRepository;
        this.cityRepository = cityRepository;
    }

    public Theatre addTheatre() {
        System.out.println("Please enter the name of the theatre");
        String theatreName = scn.next();
        System.out.println("Please enter the address of the theatre");
        String theatreAddress = scn.next();
        Theatre theatre = new Theatre();
        theatre.setTheatreName(theatreName);
        theatre.setTheatreAddress(theatreAddress);
        System.out.println("Please enter the city in which the theatre is located");
        String cityName = scn.next();
        Optional<City> cityOptional = cityRepository.findByCityName(cityName);
        if(cityOptional.isEmpty()){
            throw new RuntimeException("City not found");
        }
        cityOptional.get().getTheatreList().add(theatre);
        City city = cityRepository.save(cityOptional.get());
        theatre.setCity(city);
        return theatreRepository.save(theatre);
    }
}
