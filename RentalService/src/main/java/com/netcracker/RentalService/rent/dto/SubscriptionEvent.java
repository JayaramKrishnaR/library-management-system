package com.netcracker.RentalService.rent.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Data
public class SubscriptionEvent {
    private UUID userId;
    private String first_name;
    private String email;
    private LocalDate subEndDate;
}
