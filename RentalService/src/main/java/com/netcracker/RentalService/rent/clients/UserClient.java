package com.netcracker.RentalService.rent.clients;
import com.netcracker.RentalService.rent.dto.User;
import com.netcracker.RentalService.rent.exceptions.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class UserClient {
    static final String USERSERVICE_GETUSER_URL="http://USERSERVICE/userService/v1/sendUser/";
    static final String USERSERVICE_UPDATE_URL="http://USERSERVICE/userService/v1/user/client/";
    @Autowired
    RestTemplate restTemplate;

    public User getUser(UUID userId) throws UserServiceException {
        User user;
        user= restTemplate.getForObject(USERSERVICE_GETUSER_URL + userId, User.class);
        return user;
    }
    public void updateUser(UUID userId,User user) throws UserServiceException {
        try {
            restTemplate.put(USERSERVICE_UPDATE_URL + userId, user);
        }catch (Exception ex){throw new UserServiceException("User service not responding");}
    }
    public List<User> getSubEndingUsers(String startDate,String endDate){
        ResponseEntity<List<User>> response= restTemplate.exchange("http://USERSERVICE/userService/v1/endUser/?startDate=" + startDate + "&endDate=" + endDate, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });
        return response.getBody();
    }
}
