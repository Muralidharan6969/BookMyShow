package com.example.bookmyshow.Controllers;

import com.example.bookmyshow.DTOs.BookMovieRequestDto;
import com.example.bookmyshow.DTOs.BookMovieResponseDto;
import com.example.bookmyshow.DTOs.BookingStatus;
import com.example.bookmyshow.Models.ENUMS.TicketStatus;
import com.example.bookmyshow.Models.Ticket;
import com.example.bookmyshow.Services.TicketBookingService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Getter
@Setter
@Controller
public class TicketController {
    private TicketBookingService ticketBookingService;
    @Autowired
    TicketController(TicketBookingService ticketBookingService){
        this.ticketBookingService = ticketBookingService;
    }
    public BookMovieResponseDto bookMovie (BookMovieRequestDto bookMovieRequestDto) throws Exception {
        BookMovieResponseDto bookMovieResponseDto = new BookMovieResponseDto();
        bookMovieResponseDto.setBookingStatus(BookingStatus.INPROGRESS);
        Ticket ticket = ticketBookingService.bookMovie(bookMovieRequestDto.getUserId(),
                bookMovieRequestDto.getShowId(), bookMovieRequestDto.getShowSeatMappingId());
        if(ticket.getTicketStatus().equals(TicketStatus.CONFIRMED)){
            bookMovieResponseDto.setBookingStatus(BookingStatus.SUCCESS);
            bookMovieResponseDto.setBookingId(ticket.getId());
            System.out.println("Ticket booked successfully");
            System.out.println("Please find the ticket details below");
            System.out.println("Ticket Id: " + ticket.getId());
            System.out.println("Movie Name: " + ticket.getShow().getMovie().getMovieName());
            System.out.println("Theatre Name: " + ticket.getShow().getScreen().getTheatre().getTheatreName());
            System.out.println("Screen Name: " + ticket.getShow().getScreen().getScreenName());
            System.out.println("Show Date: " + ticket.getShow().getShowDate());
            System.out.println("Show Start Time: " + ticket.getShow().getShowStartTime());
            System.out.println("Seats Booked: ");
            ticket.getSeats().forEach(showSeatMapping -> {
                System.out.println("Seats Booked : " + showSeatMapping.getSeat().getSeatNumber());
            });
            System.out.println("Ticket Price: " + ticket.getTotalAmount());
            System.out.println("Ticket Booked At: " + ticket.getBookedAt());
            System.out.println("Ticket Booked By: " + ticket.getBookedByUser().getUserName());
        }
        else if(ticket.getTicketStatus().equals(TicketStatus.PENDING)){
            bookMovieResponseDto.setBookingStatus(BookingStatus.FAILURE);
        }
        return bookMovieResponseDto;
    }
}
