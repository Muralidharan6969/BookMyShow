package com.example.bookmyshow.Models;

import com.example.bookmyshow.Models.ENUMS.TicketStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity (name = "TICKETS")
public class Ticket extends BaseModel{
    @Enumerated(EnumType.ORDINAL)
    private TicketStatus ticketStatus;
    private String ticketNumber;
    private int totalAmount;
    private LocalDateTime bookedAt;

    @OneToMany (mappedBy = "ticket", fetch = FetchType.EAGER)
    private List<ShowSeatMapping> seats = new ArrayList<>();

    @ManyToOne
    private Show show;

    @ManyToOne
    private User bookedByUser;

    @OneToOne
    private Payment payment;
}
