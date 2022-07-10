package com.example.Account_Service.ExceptionSpring;

import javax.validation.constraints.Min;

public class FlightInfo {
    @Min(1)
    private long id;

    private String from;

    private String to;

    private String gate;

    // constructor, getters, setters

    public FlightInfo() {
    }

    public FlightInfo(long id, String from, String to, String gate) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.gate = gate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }
}