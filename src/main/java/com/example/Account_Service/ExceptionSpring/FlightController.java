package com.example.Account_Service.ExceptionSpring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class FlightController {

    private final List<FlightInfo> flightInfoList = Collections.synchronizedList(
            new ArrayList<>());

    public FlightController() {
        flightInfoList.add(
                new FlightInfo(
                        1,
                        "Delhi Indira Gandhi",
                        "Stuttgart",
                        "D80"));
        flightInfoList.add(
                new FlightInfo(
                        2,
                        "Tokyo Haneda",
                        "Frankfurt",
                        "110"));
    }

    @GetMapping("flights/{id}")
    public FlightInfo getFlightInfo(@PathVariable long id) {
        for (var flightInfo : flightInfoList) {
            if (flightInfo.getId() == id) {
                return flightInfo;
            }
        }

        throw new FlightNotFoundException("Flight info not found id=" + id);
    }

    @PostMapping("/flights/new")
    public void addNewFlightInfo(@Valid @RequestBody FlightInfo flightInfo) {
        flightInfoList.add(flightInfo);
    }

//    @ExceptionHandler(FlightNotFoundException.class)
//    public ResponseEntity<CustomErrorMessage> handleFlightNotFound(
//            FlightNotFoundException e, WebRequest request) {
//
//        CustomErrorMessage body = new CustomErrorMessage(
//                HttpStatus.NOT_FOUND.value(),
//                LocalDateTime.now(),
//                e.getMessage(),
//                request.getDescription(false));
//
//        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
//    }

}