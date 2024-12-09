package com.netcracker.userService.User.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.netcracker.userService.subscription.entity.Subscription;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "testuser")
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid") @Column(length = 36)
    private UUID userId;
    private String first_name;
    private String last_name;
    private long contact_no;
    private String email;
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate subStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate subEndDate;
    String subscriptionType;
    private String password;
    private int credit;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sub_id",referencedColumnName = "id")
    Subscription subscription;
    @JsonBackReference
    public Subscription getSubscription() {
        return subscription;
    }
    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
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
                ", subscription=" + subscription +
                '}';
    }
}


