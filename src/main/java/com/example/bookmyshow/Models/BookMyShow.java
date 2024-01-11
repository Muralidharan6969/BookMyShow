package com.example.bookmyshow.Models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class BookMyShow {
    private List<City> cities = new ArrayList<>();
}
