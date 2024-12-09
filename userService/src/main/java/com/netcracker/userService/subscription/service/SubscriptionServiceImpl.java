package com.netcracker.userService.subscription.service;

import com.netcracker.userService.User.exceptions.ResourceNotFound;
import com.netcracker.userService.subscription.dao.SubscriptionRepository;
import com.netcracker.userService.subscription.entity.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{
    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Override
    public Subscription createSubscription(Subscription sub) {
        return subscriptionRepository.save(sub);
    }
    @Override
    public Subscription getSubscription(UUID id) {
        return subscriptionRepository.findById(id).orElseThrow(()->new ResourceNotFound("Subscription","Id",id));
    }

    @Override
    public List<Subscription> getAllSubscritions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public String deleteSubscription(UUID subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
        return "Subscription deleted";
    }
}
