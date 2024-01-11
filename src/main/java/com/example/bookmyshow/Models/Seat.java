package com.example.bookmyshow.Models;

import com.example.bookmyshow.Models.ENUMS.SeatType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity (name = "SEATS")
public class Seat extends BaseModel{
    private String seatNumber;
    private int seatRow;
    private int seatCol;

    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;

    @OneToMany(mappedBy = "seat", fetch = FetchType.EAGER)
    private List<ShowSeatMapping> showSeatMappingList = new ArrayList<>();

    @ManyToOne
    private Screen screen;
}
