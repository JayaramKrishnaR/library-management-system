package com.netcracker.userService.subscription.dao;

import com.netcracker.userService.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,UUID> {

    @Override
    Optional<Subscription> findById(UUID integer);
}
