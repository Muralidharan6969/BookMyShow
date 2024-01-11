package com.example.bookmyshow.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMovieResponseDto {
    private BookingStatus bookingStatus;
    private Long bookingId;
}
