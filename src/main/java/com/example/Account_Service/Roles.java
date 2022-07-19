package com.example.Account_Service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
public class Roles {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "bigint not null")
    @JsonIgnore
    private Long id;

    @NotEmpty
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
    REMOVE(1),
    GRANT(2),
    LOCK(3),
    UNLOCK(4);

    private int identityOperation;

    public int getIdentityOperation() {
        return identityOperation;
    }

    public void setIdentityOperation(int identityOperation) {
        this.identityOperation = identityOperation;
    }

    operation(int identityOperation) {
        this.identityOperation = identityOperation;
    }
}
