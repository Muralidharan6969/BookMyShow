package com.example.bookmyshow.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity (name = "CITIES")
public class City extends BaseModel{
    private String cityName;

    @OneToMany (mappedBy = "city", fetch = FetchType.EAGER)
    private List<Theatre> theatreList = new ArrayList<>();

    /*
    @ManyToMany
    private List<Movie> movieList;
     */


}
