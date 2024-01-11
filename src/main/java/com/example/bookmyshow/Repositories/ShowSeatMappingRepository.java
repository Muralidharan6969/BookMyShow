package com.example.bookmyshow.Repositories;

import com.example.bookmyshow.Models.ShowSeatMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ShowSeatMappingRepository extends JpaRepository<ShowSeatMapping, Long> {
    @Override
    List<ShowSeatMapping> findAllById(Iterable<Long> showSeatIds);

    @Override
    ShowSeatMapping save(ShowSeatMapping showSeatMapping);
}
