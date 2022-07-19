package com.example.Account_Service.ExceptionSpring;

import com.example.Account_Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class ControllerExceptionHandler {

    @Autowired
    LogRepository logRepository;

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


//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<?> handleUserNotAuth(
//            AuthenticationException e, HttpServletRequest request) {
//        CustomErrorMessageUsers body = new CustomErrorMessageUsers(
//                LocalDateTime.now().toString(),
//                HttpStatus.UNAUTHORIZED.value(),
//                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
//                "e.getMessage()",
//                request.getServletPath());
//        Log log = new Log();
//        log.setPath(request.getServletPath());
//        log.setObject(request.getServletPath());
//        log.setAction("ACCESS_DENIED");
//        log.setSubject(request.getRemoteUser());
//        logRepository.save(log);
//        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
//    }

}