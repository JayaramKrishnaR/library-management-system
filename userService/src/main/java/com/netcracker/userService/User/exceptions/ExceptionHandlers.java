package com.netcracker.userService.User.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(value = InsufficientBalanceException.class)
    public ResponseEntity<Object> InsufficentBalance(InsufficientBalanceException ex){
        String message=ex.getMessage();
        return new ResponseEntity(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = ResourceNotFound.class)
    public ResponseEntity<Object> ResourceNotAvailable(ResourceNotFound ex){
        String message=ex.getMessage();
        return new ResponseEntity(message, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> UserNotAvailable(UserNotFoundException ex){
        String message=ex.getMessage();
        return new ResponseEntity(message, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = InvalidCredentialException.class)
    public ResponseEntity<Object> InvalidCredentials(InvalidCredentialException ex){
        String message=ex.getMessage();
        return new ResponseEntity(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> ArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String,String> errorMap=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error-> errorMap.put(error.getField(),error.getDefaultMessage()));
        return new ResponseEntity(errorMap, HttpStatus.UNAUTHORIZED);
    }
}
