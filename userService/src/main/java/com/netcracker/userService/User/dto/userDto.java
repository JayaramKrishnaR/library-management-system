package com.netcracker.userService.User.dto;

import com.netcracker.userService.User.validator.EmailExist;
import com.netcracker.userService.subscription.entity.Subscription;
import lombok.*;
import javax.validation.constraints.*;
import java.util.UUID;

@NoArgsConstructor
@Data
public class userDto {
        private UUID userId;
        @NotEmpty(message = "Name cannot be empty")
        private String first_name;
        @NotEmpty(message = "This field cannot be empty")
        private String last_name;
        @NotNull(message = "Must specify contact number")
        @Min(value = 1000000000,message = "Contact number must be of atleast 10 digits")
        private Long contact_no;
        @Email(message = "Email not valid")
        @EmailExist
        private String email;
        @NotEmpty(message = "Address cannot be empty")
        private String address;
        @NotEmpty(message = "Password must not be empty")
        private String password;
        private Subscription subscription;

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



