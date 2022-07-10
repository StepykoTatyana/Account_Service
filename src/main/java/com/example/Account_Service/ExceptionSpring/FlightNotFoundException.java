package com.example.Account_Service.ExceptionSpring;


public class FlightNotFoundException extends RuntimeException {

    public FlightNotFoundException(String message) {
        super(message);
    }


}
