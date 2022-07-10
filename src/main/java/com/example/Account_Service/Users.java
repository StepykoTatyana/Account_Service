package com.example.Account_Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Component
@Entity
public class Users {
    public Users() {
    }

    @Id
    @GeneratedValue
    @Column(columnDefinition = "bigint not null constraint user_pkey primary key")
    private Long id;

    @Column
    @NotEmpty
    private  String name;

    @Column
    @NotEmpty
    private  String lastname;

    @Column
    @NotEmpty
    @Pattern(regexp = ".+@acme.com$")
    private  String email;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @NotNull
    @Size(min = 12, message = "The password length must be at least 12 chars!")
    private String password;

    @Column
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    @JsonIgnore
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users(String name, String lastname, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
