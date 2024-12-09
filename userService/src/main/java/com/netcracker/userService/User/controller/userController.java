package com.netcracker.userService.User.controller;

import com.netcracker.userService.User.dto.userClientDto;
import com.netcracker.userService.User.entity.User;
import com.netcracker.userService.User.service.UserService;
import com.netcracker.userService.User.dto.userDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/userService")
public class userController {
    @Autowired
    UserService userService;
    @PostMapping(value = "/v1/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody userDto user){
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }
    @GetMapping(value = "/v1/user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") UUID userId){
        return new ResponseEntity<>(userService.getUser(userId),HttpStatus.OK);
    }
    @GetMapping(value = "/v1/user")
    public ResponseEntity<List<User>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }
    @GetMapping(value = "/v1/sendUser/{userId}")
    public User sendUser(@PathVariable("userId") UUID userId){
        return userService.sendUser(userId);
    }
    @PutMapping(value = "/v1/user/client/{userId}")
    public ResponseEntity<User> updateClientUser(@PathVariable("userId") UUID userId,@Valid @RequestBody userClientDto user){
        return new ResponseEntity<>(userService.updateClientUser(userId,user),HttpStatus.CREATED);
    }

    @PutMapping(value = "/v1/user/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") UUID userId,@Valid @RequestBody userDto user){
        return new ResponseEntity<>(userService.updateUser(userId,user),HttpStatus.CREATED);
    }
    @PostMapping(value = "/v1/login")
    public ResponseEntity<User> loggedUser(@RequestParam("email") String email,@RequestParam("password") String password){

        return new ResponseEntity<>(userService.validateUser(email,password),HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/user/userId")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") UUID userId){
        return new ResponseEntity<>(userService.deleteUser(userId),HttpStatus.OK);
    }

    @GetMapping(value = "/v1/endUser/")
    public List<User> getSubEndingUser(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate ){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return userService.getSubEndingsUser(LocalDate.parse(startDate,formatter),LocalDate.parse(endDate,formatter));
    }
}
