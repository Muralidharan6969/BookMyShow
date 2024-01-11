package com.example.bookmyshow.Services;

import com.example.bookmyshow.Exceptions.ShowNotFoundException;
import com.example.bookmyshow.Exceptions.ShowSeatNotFoundException;
import com.example.bookmyshow.Exceptions.UserNotFoundException;
import com.example.bookmyshow.Models.ENUMS.PaymentStatus;
import com.example.bookmyshow.Models.ENUMS.SeatStatus;
import com.example.bookmyshow.Models.ENUMS.TicketStatus;
import com.example.bookmyshow.Models.Show;
import com.example.bookmyshow.Models.ShowSeatMapping;
import com.example.bookmyshow.Models.Ticket;
import com.example.bookmyshow.Models.User;
import com.example.bookmyshow.Repositories.ShowRepository;
import com.example.bookmyshow.Repositories.ShowSeatMappingRepository;
import com.example.bookmyshow.Repositories.TicketRepository;
import com.example.bookmyshow.Repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Service
public class TicketBookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatMappingRepository showSeatMappingRepository;
    private PriceCalculator priceCalculator;
    private PaymentService paymentService;
    private TicketRepository ticketRepository;

    @Autowired
    TicketBookingService(UserRepository userRepository, ShowRepository showRepository,
                         ShowSeatMappingRepository showSeatMappingRepository,
                         PriceCalculator priceCalculator,
                         PaymentService paymentService,
                         TicketRepository ticketRepository){
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatMappingRepository = showSeatMappingRepository;
        this.priceCalculator = priceCalculator;
        this.paymentService = paymentService;
        this.ticketRepository = ticketRepository;
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookMovie(Long userId, Long showId, List<Long> showSeatIds) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        User bookedBy = userOptional.get();
        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()){
            throw new ShowNotFoundException("Show not found");
        }
        Show bookedShow = showOptional.get();
        List<ShowSeatMapping> showSeats = showSeatMappingRepository.findAllById(showSeatIds);
        for(ShowSeatMapping showSeatMapping : showSeats){
            if(!showSeatMapping.getSeatStatus().equals(SeatStatus.AVAILABLE)){
                throw new ShowSeatNotFoundException("ShowSeats not available");
            }
        }
        List<ShowSeatMapping> savedShowSeats = new ArrayList<>();
        for(ShowSeatMapping showSeatMapping : showSeats){
            showSeatMapping.setSeatStatus(SeatStatus.BLOCKED);
            savedShowSeats.add(showSeatMappingRepository.save(showSeatMapping));
        }
        Ticket ticket = new Ticket();
        ticket.setTicketStatus(TicketStatus.PENDING);
        ticket.setTotalAmount(priceCalculator.finalAmount(savedShowSeats, bookedShow));
        ticket.setPayment(paymentService.makePayment(ticket.getTotalAmount()));
        if (ticket.getPayment().getPaymentStatus().equals(PaymentStatus.CONFIRMED)){
            for(ShowSeatMapping showSeatMapping : savedShowSeats){
                showSeatMapping.setSeatStatus(SeatStatus.BOOKED);
                showSeatMappingRepository.save(showSeatMapping);
            }
            ticket.setTicketStatus(TicketStatus.CONFIRMED);
            ticket.setBookedByUser(bookedBy);
            ticket.setBookedAt(LocalDateTime.now());
            ticket.setShow(bookedShow);
            ticket.setSeats(savedShowSeats);
        }
        else {
            for(ShowSeatMapping showSeatMapping : savedShowSeats){
                showSeatMapping.setSeatStatus(SeatStatus.AVAILABLE);
                showSeatMappingRepository.save(showSeatMapping);
            }
        }
        return ticketRepository.save(ticket);
    }
}
