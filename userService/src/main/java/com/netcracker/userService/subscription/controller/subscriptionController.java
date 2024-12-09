package com.netcracker.userService.subscription.controller;

import com.netcracker.userService.subscription.dto.SubscriptionDto;
import com.netcracker.userService.subscription.entity.Subscription;
import com.netcracker.userService.User.entity.User;
import com.netcracker.userService.subscription.service.SubscriptionService;
import com.netcracker.userService.User.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/userService")
public class subscriptionController {
    @Autowired
    UserService userService;
    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    ModelMapper mapper;

    @PostMapping(value = "/v1/subscription")
    public ResponseEntity<Subscription> createSub(@RequestBody Subscription subscription){
        return new ResponseEntity<>(subscriptionService.createSubscription(subscription), HttpStatus.CREATED);
    }
    @GetMapping(value = "/v1/getAllSubscription")
    public ResponseEntity<List<SubscriptionDto>> getAllSubscription(){
        List<Subscription> subscriptions= subscriptionService.getAllSubscritions();
        List<SubscriptionDto> subscriptionDtos=subscriptions.stream().map((subscription -> mapper.map(subscription,SubscriptionDto.class))).collect(Collectors.toList());
        return new ResponseEntity<>(subscriptionDtos,HttpStatus.OK);
    }
    @GetMapping(value = "/v1/subscription/{subId}")
    public SubscriptionDto getSub(@PathVariable("subId") UUID subId){
        Subscription subscription=subscriptionService.getSubscription(subId);
        SubscriptionDto subscriptionDto=mapper.map(subscription,SubscriptionDto.class);
        return subscriptionDto;
    }

    @PutMapping("/v1/user/{userId}/subscription/{subId}")
    public ResponseEntity<User> setSubscription(@PathVariable("userId") UUID userId, @PathVariable("subId") UUID subId){
        return new ResponseEntity<>(userService.setSubscription(userId,subId),HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/subscription/{subId}")
    public ResponseEntity<String> deleteSubscriptionById(@PathVariable("subId") UUID subscriptionId){
        return new ResponseEntity<>(subscriptionService.deleteSubscription(subscriptionId),HttpStatus.OK);
    }
}
