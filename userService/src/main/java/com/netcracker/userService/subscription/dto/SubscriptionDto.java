package com.netcracker.userService.subscription.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
@NoArgsConstructor
@Data
public class SubscriptionDto {
    private UUID id;
    private String type;
    private int cost;
    private int credit;
}
