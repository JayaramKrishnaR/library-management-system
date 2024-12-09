package com.netcracker.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID userId;
    private String first_name;
    private String last_name;
    private long contact_no;
    private String email;
    private String address;
//    private LocalDate subStartDate;
//    private LocalDate subEndDate;
    private String password;
    private int credit;
}
