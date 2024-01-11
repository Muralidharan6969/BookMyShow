package com.example.bookmyshow.Models;

import com.example.bookmyshow.Models.ENUMS.Feature;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity (name = "SHOWS")
public class Show extends BaseModel{
    private LocalDate showDate;
    private LocalTime showStartTime;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Screen screen;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> showFeatures;

    @OneToMany (mappedBy = "show", fetch = FetchType.EAGER)
    private List<ShowSeatMapping> showSeatMappingList = new ArrayList<>();

    @OneToMany (mappedBy = "show", fetch = FetchType.EAGER)
    private List<Ticket> ticketList = new ArrayList<>();

    @OneToMany (mappedBy = "show", fetch = FetchType.EAGER)
    private List<ShowSeatType> showSeatTypeList = new ArrayList<>();
}
