package com.example.Account_Service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


@Entity
public class Roles {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "bigint not null")
    @JsonIgnore
    private Long id;

    @Column(name = "email")
    private String user;

    public String getUser() {
        return user.toLowerCase();
    }

    public void setUser(String user) {
        this.user = user.toLowerCase();
    }

    @Column
    private String role;

    @Transient
    operation operation;

    public Roles() {
    }

    public Roles(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public operation getOperation() {
        return operation;
    }

    public void setOperation(operation operation) {
        this.operation = operation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String roles) {
        this.role = roles;
    }
}


enum operation {
    REMOVE,
    GRANT
}
