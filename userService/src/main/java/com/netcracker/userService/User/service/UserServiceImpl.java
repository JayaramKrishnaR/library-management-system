package com.netcracker.userService.User.service;

import com.netcracker.userService.User.dao.UserRepository;
import com.netcracker.userService.User.dto.userClientDto;
import com.netcracker.userService.User.dto.userDto;
import com.netcracker.userService.User.exceptions.InsufficientBalanceException;
import com.netcracker.userService.User.exceptions.InvalidCredentialException;
import com.netcracker.userService.User.exceptions.ResourceNotFound;
import com.netcracker.userService.User.exceptions.UserNotFoundException;
import com.netcracker.userService.subscription.dao.SubscriptionRepository;
import com.netcracker.userService.subscription.entity.Subscription;
import com.netcracker.userService.User.entity.User;
import com.netcracker.userService.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    SubscriptionService subscriptionService;
    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Autowired
    ModelMapper mapper;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(userDto user) {
        return userRepository.save(mapper.map(user, User.class));
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFound("User","Id",id));
    }

    @Override
    public String deleteUser(UUID id) {
        userRepository.deleteById(id);
        return "User deleted";
    }

    @Override
    public User updateUser(UUID id, userDto user) {
        User existingUser=userRepository.findById(id).orElseThrow(()->new ResourceNotFound("User","Id",id));

        existingUser.setUserId(id);
        existingUser.setFirst_name(user.getFirst_name());
        existingUser.setLast_name(user.getLast_name());
        existingUser.setAddress(user.getAddress());
        existingUser.setContact_no(user.getContact_no());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);
    }
    public User updateClientUser(UUID id, userClientDto user) {
        User existingUser=userRepository.findById(id).get();
        User updatedUser=mapper.map(user, User.class);
        updatedUser.setSubStartDate(existingUser.getSubStartDate());
        updatedUser.setSubEndDate(existingUser.getSubEndDate());
        updatedUser.setSubscriptionType(existingUser.getSubscriptionType());
        updatedUser.setSubscription(existingUser.getSubscription());
        updatedUser.setUserId(id);
        return userRepository.save(updatedUser);//change
    }

    @Override
    public User setSubscription(UUID userId, UUID subId) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFound("User","Id",userId));
        Subscription sub=subscriptionService.getSubscription(subId);
        user.setSubStartDate(LocalDate.now());
        user.setSubEndDate(user.getSubStartDate().plusMonths(1));
        user.setSubscription(sub);
        user.setSubscriptionType(sub.getType());
        user.setCredit(sub.getCredit());
        List<User> users=sub.getUsers();
        users.add(user);
        sub.setUsers(users);
        subscriptionRepository.save(sub);
        return userRepository.save(user);
    }
    @Scheduled(cron = "0 50 23 * * *")
    public void removeSubscriptionForSubEndUsers(){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate=LocalDate.parse(LocalDate.now().toString(),formatter);
        System.out.println(currentDate);
        List<User> subEndedUser=this.getUserByEndDate(currentDate);

        subEndedUser.stream().forEach(user -> {userClientDto userDto=mapper.map(user,userClientDto.class);
            User existUser=mapper.map(userDto,User.class);
            existUser.setCredit(0);
            existUser.setUserId(user.getUserId());
            userRepository.save(existUser);
        });
    }

    @Override
    public User sendUser(UUID id) throws InsufficientBalanceException {

        User user= userRepository.findById(id).orElseThrow(()->new UserNotFoundException("USER","Id",id));
        if(user.getCredit()<=0){throw new InsufficientBalanceException("Insufficient Balance","Id",id);}
        return user;
    }

    public List<User> getSubEndingsUser(LocalDate startDate,LocalDate endDate){
        return userRepository.findAllBySubEndDateBetween(startDate,endDate);
    }

    @Override
    public List<User> getUserByEndDate(LocalDate date) {
        return userRepository.findAllBySubEndDate(date);
    }

    @Override
    public User validateUser(String email, String password) {
        User user=userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFound("user with email not present"));
        if(user.getEmail().equals(email)&&user.getPassword().equals(password)) {
            return user;
        }
        throw new InvalidCredentialException("User name or password invalid");
    }
}
