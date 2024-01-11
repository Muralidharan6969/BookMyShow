package com.example.bookmyshow.Repositories;

import com.example.bookmyshow.Models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Override
    Optional<Seat> findById(Long aLong);

    @Override
    Seat save(Seat seat);
}
