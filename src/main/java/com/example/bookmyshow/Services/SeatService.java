package com.example.bookmyshow.Services;

import com.example.bookmyshow.Models.ENUMS.SeatType;
import com.example.bookmyshow.Models.Screen;
import com.example.bookmyshow.Models.Seat;
import com.example.bookmyshow.Repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class SeatService {
    private Scanner scn = new Scanner(System.in);
    private SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat addSeat(int seatRow, int seatColumn, Screen screen, SeatType seatType){
        Seat seat = new Seat();
        seat.setSeatRow(seatRow);
        seat.setSeatCol(seatColumn);
        seat.setScreen(screen);
        seat.setSeatType(seatType);
        seat.setSeatNumber(getRCSeatNumber(seatRow, seatColumn));
        return seatRepository.save(seat);
    }

    public String getRCSeatNumber(int row, int col){
        String rowString = getCharForNumber(row);
        String colString = Integer.toString(col);
        return rowString + colString;
    }

    public String getCharForNumber(int i) {
        StringBuffer sb = new StringBuffer();
        while (i > 0) {
            int rem = i % 26;
            if (rem == 0) {
                sb.append('Z');
                i = (i / 26) - 1;
            } else {
                sb.append((char)((rem - 1) + 'A'));
                i = i / 26;
            }
        }
        return sb.reverse().toString();
    }
}
