package com.example.bookmyshow.Repositories;

import com.example.bookmyshow.Models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    @Override
    Optional<Show> findById(Long aLong);

    List<LocalDate> findAllByMovieId(Long movieId);
}
