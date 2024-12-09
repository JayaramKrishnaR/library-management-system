package com.netcracker.RentalService.rent.entity;

import com.netcracker.RentalService.rent.enumerations.Status;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "rent")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rent {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid") @Column(length = 36)
    UUID issue_id;
    UUID userId;
    String user_name;
    UUID book_id;
    String book_title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate issue_date;
    @Enumerated(EnumType.STRING)
    Status status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate due_date;

    @Override
    public String toString() {
        return "Rent{" +
                "issue_id=" + issue_id +
                ", user_id=" + userId +
                ", user_name='" + user_name + '\'' +
                ", book_id=" + book_id +
                ", book_title='" + book_title + '\'' +
                ", issue_date=" + issue_date +
                ", status='" + status + '\'' +
                ", due_date=" + due_date +
                '}';
    }
}
