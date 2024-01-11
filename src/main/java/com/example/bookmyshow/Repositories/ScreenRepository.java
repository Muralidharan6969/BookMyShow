package com.example.bookmyshow.Repositories;

import com.example.bookmyshow.Models.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
    Optional<Screen> findByScreenName(String screenName);
    @Override
    Screen save(Screen screen);
}
