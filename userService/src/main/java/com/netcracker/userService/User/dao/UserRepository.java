package com.netcracker.userService.User.dao;

import com.netcracker.userService.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,UUID> {
    List<User> findAllBySubEndDateBetween(LocalDate startDate,LocalDate endDate);
    @Override
    Optional<User> findById(UUID userId);
    List<User> findAllBySubEndDate(LocalDate date);
    @Override
    void deleteById(UUID integer);
    Optional<User> findByEmail(String email);
}
