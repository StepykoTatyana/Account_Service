package com.example.Account_Service;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Component
public class Log {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
    @Column(name = "log_id", columnDefinition = "bigint not null")
    private Long id;
    @Column
    private String date = LocalDateTime.now().toString();
    @Column
    private String action;


    public Log(String action, String subject, String object, String path) {
        this.action = action;
        this.subject = subject;
        this.object = object;
        this.path = path;

    }


    @Column
    private String subject;
    @Column
    private String object;
    @Column
    private String path;

    public Log() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
