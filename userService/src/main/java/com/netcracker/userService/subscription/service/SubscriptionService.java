package com.netcracker.userService.subscription.service;

import com.netcracker.userService.subscription.entity.Subscription;
import java.util.List;
import java.util.UUID;

public interface SubscriptionService {
    Subscription createSubscription(Subscription sub);
    Subscription getSubscription(UUID id);
    List<Subscription> getAllSubscritions();
    String deleteSubscription(UUID subscriptionId);
}
