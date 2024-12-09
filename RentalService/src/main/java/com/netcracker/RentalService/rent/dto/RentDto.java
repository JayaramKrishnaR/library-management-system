package com.netcracker.RentalService.rent.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class RentDto {
    private UUID issue_id;
    private UUID userId;
    private String user_name;
    private UUID book_id;
    private String book_title;
    private Date issue_date;
    private String status;
    private Date due_date;
}
