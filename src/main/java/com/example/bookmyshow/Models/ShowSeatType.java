package com.example.bookmyshow.Models;

import com.example.bookmyshow.Models.ENUMS.SeatType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShowSeatType extends BaseModel{
    @ManyToOne
    private Show show;

    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;

    private int price;

    public ShowSeatType(Show show, SeatType seatType, int price) {
        this.show = show;
        this.seatType = seatType;
        this.price = price;
    }

    public ShowSeatType() {
    }
}
