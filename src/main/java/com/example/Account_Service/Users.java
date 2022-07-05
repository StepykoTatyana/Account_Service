package com.example.Account_Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


public class Users {
    public Users() {
    }

    @NotEmpty
    private  String name;

    @NotEmpty
    private  String lastname;

    @NotEmpty
    @Pattern(regexp = ".+@acme.com$")
    private  String email;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @NotNull
    private String password;

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
