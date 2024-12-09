package com.netcracker.userService.User.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailPresentValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailExist {
    String message() default "Email already present";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
