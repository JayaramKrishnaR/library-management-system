package com.netcracker.RentalService.rent.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;


@NoArgsConstructor
@Data
public class User {
    private UUID userId;
    private String first_name;
    private String last_name;
    private long contact_no;
    private String email;
    private String address;

    LocalDate subStartDate;

    LocalDate subEndDate;
    private String password;

    private int credit;

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + userId +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", contact_no=" + contact_no +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
