package com.example.bookmyshow.Models;

import com.example.bookmyshow.Models.ENUMS.SeatStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.security.PrivateKey;

@Getter
@Setter
@Entity (name = "SHOW_SEAT_MAPPING")
public class ShowSeatMapping extends BaseModel{
    @ManyToOne
    private Show show;

    @ManyToOne
    private Seat seat;

    @ManyToOne
    private Ticket ticket;

    @Enumerated(EnumType.ORDINAL)
    private SeatStatus seatStatus;
}
