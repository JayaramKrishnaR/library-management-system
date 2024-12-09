package com.netcracker.userService.User.service;

import com.netcracker.userService.User.dto.userClientDto;
import com.netcracker.userService.User.entity.User;
import com.netcracker.userService.User.dto.userDto;
import com.netcracker.userService.User.exceptions.InsufficientBalanceException;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UserService{
    List<User> getAllUsers();
    User createUser(userDto user);
    User getUser(UUID id);
    String deleteUser(UUID id);
    User updateUser(UUID id, userDto user);
    User updateClientUser(UUID id, userClientDto user);

    User setSubscription(UUID userId, UUID subId);

    User sendUser(UUID id) throws InsufficientBalanceException;
    List<User> getSubEndingsUser(LocalDate startDate, LocalDate endDate);

    List<User> getUserByEndDate(LocalDate date);

    User validateUser(String email, String password);
}
