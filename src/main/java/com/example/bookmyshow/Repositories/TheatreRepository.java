package com.example.bookmyshow.Repositories;

import com.example.bookmyshow.Models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    Optional<Theatre> findByTheatreName(String theatreName);

    @Override
    Theatre save(Theatre theatre);
}
