package com.netcracker.notificationservice.client;

import com.netcracker.notificationservice.dto.UserDto;
import com.netcracker.notificationservice.exceptions.UserServiceNotRespondingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class UserClient {
    private static final String USERSERVICE_GETUSER_URL="http://USERSERVICE/userService/v1/user/";

    @Autowired
    RestTemplate restTemplate;

    public UserDto getUserById(UUID userId) throws UserServiceNotRespondingException {
       try {
            UserDto userDto = restTemplate.getForObject(USERSERVICE_GETUSER_URL + userId, UserDto.class);
            return userDto;
        }
        catch(Exception ex){
            throw new UserServiceNotRespondingException("User service not responding");
        }
    }
}
