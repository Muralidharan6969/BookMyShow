package com.example.bookmyshow.Models;

import com.example.bookmyshow.Models.ENUMS.MovieFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity (name = "MOVIES")
public class Movie extends BaseModel{
    private String movieName;
    private String language;

    @Enumerated(EnumType.ORDINAL)
    private MovieFormat movieFormat;

    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    private List<Show> shows = new ArrayList<>();
}
