package com.example.bookmyshow.Repositories;

import com.example.bookmyshow.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long aLong);
    Optional<User> findByEmailId(String email);

    @Override
    User save(User user);
}
