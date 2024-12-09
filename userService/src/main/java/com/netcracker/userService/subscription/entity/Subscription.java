package com.netcracker.userService.subscription.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.netcracker.userService.User.entity.User;
import lombok.*;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Subscription")
@NoArgsConstructor
@Data
public class Subscription {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid") @Column(length = 36)
    private UUID id;
    private String type;
    private int cost;
    private int credit;

    @OneToMany(mappedBy = "subscription")
    List<User> users=new ArrayList<>();

    @JsonManagedReference
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", cost=" + cost +
                ", credit=" + credit +
                ", users=" + users +
                '}';
    }
}
