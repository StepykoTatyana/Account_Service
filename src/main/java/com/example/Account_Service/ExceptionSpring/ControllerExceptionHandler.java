package com.example.Account_Service.ExceptionSpring;

import com.example.Account_Service.CustomErrorMessageUsers;
import com.example.Account_Service.UserExistException;
import com.example.Account_Service.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<CustomErrorMessageUsers> exists(
            UserExistException e, HttpServletRequest request) {

        CustomErrorMessageUsers body = new CustomErrorMessageUsers(
                LocalDateTime.now().toString(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                e.getMessage(),
                request.getServletPath());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorMessageUsers> validateExc(
            MethodArgumentNotValidException m, HttpServletRequest request) {


        CustomErrorMessageUsers body = new CustomErrorMessageUsers(
                LocalDateTime.now().toString(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                Objects.requireNonNull(m.getBindingResult().getFieldError()).getDefaultMessage(),
                request.getServletPath());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomErrorMessageUsers> validateException(
            ConstraintViolationException m, HttpServletRequest request) {


        CustomErrorMessageUsers body = new CustomErrorMessageUsers(
                LocalDateTime.now().toString(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                m.getMessage(),
                request.getServletPath());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

    }



    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<CustomErrorMessageUsers> handleUserNotFound(
            UserNotExistException e, HttpServletRequest request) {

        CustomErrorMessageUsers body = new CustomErrorMessageUsers(
                LocalDateTime.now().toString(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                e.getMessage(),
                request.getServletPath());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorMessageUsers> handleUserNotFound(
            AccessDeniedException e, HttpServletRequest request) {

        CustomErrorMessageUsers body = new CustomErrorMessageUsers(
                LocalDateTime.now().toString(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                "Access Denied!",
                request.getServletPath());

        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

}