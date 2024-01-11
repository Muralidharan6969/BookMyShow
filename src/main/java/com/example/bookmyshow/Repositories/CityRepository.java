package com.example.bookmyshow.Repositories;

import com.example.bookmyshow.Models.City;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Override
    List<City> findAll();

    @Override
    Optional<City> findById(Long aLong);

    Optional<City> findByCityName(String cityName);

    @Override
    City save(City city);
}
