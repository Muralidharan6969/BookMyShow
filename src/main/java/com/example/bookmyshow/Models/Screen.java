package com.example.bookmyshow.Models;

import com.example.bookmyshow.Models.ENUMS.Feature;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity (name = "SCREENS")
public class Screen extends BaseModel{
    private String screenName;
    private int totalSeats;

    @ManyToOne
    private Theatre theatre;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> screenFeatures = new ArrayList<>();

    @OneToMany (mappedBy = "screen", fetch = FetchType.EAGER)
    private List<Seat> seatList = new ArrayList<>();

    @OneToMany (mappedBy = "screen", fetch = FetchType.EAGER)
    private List<Show> showList = new ArrayList<>();

}
