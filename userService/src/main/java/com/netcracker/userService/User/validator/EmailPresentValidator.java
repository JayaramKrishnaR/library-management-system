package com.netcracker.userService.User.validator;

import com.netcracker.userService.User.dao.UserRepository;
import com.netcracker.userService.User.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class EmailPresentValidator implements ConstraintValidator<EmailExist,String> {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> user=userRepository.findByEmail(email);
        return user.isEmpty();
    }
}
