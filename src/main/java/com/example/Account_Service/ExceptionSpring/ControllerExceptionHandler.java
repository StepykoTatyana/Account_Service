package com.example.Account_Service.ExceptionSpring;

import com.example.Account_Service.CustomErrorMessageUsers;
import com.example.Account_Service.UserExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<CustomErrorMessageUsers> handleUserNotFound(
            UserExistException e, HttpServletRequest request) {

        CustomErrorMessageUsers body = new CustomErrorMessageUsers(
                LocalDateTime.now(),
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
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                Objects.requireNonNull(m.getFieldError()).getDefaultMessage(),
                request.getServletPath());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

    }
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//
//        // Just like a POJO, a Map is also converted to a JSON key-value structure
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("status", status.value());
//        body.put("exception", ex.getClass());
//        return new ResponseEntity<>(body, headers, status);
//    }
}