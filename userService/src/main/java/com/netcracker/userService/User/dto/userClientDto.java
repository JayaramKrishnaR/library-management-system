package com.netcracker.userService.User.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.util.UUID;

@NoArgsConstructor
@Data
public class userClientDto {
    private UUID userId;
    @NotEmpty(message = "Name cannot be empty")
    private String first_name;
    @NotEmpty(message = "This field cannot be empty")
    String last_name;
    @NotNull(message = "Must specify contact number")
    @Min(9)
    private long contact_no;
    @Email(message = "Email not valid")
    private String email;
    @NotEmpty(message = "Address cannot be empty")
    private String address;
    @NotEmpty(message = "Password must not be empty")
    private String password;
    private int credit;
}
