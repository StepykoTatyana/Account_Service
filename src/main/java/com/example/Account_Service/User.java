package com.example.Account_Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;


@Component
@Entity(name = "users")
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    public User() {
    }

    @Id
    @GeneratedValue
    @Column(name = "user_id", columnDefinition = "bigint not null")
    private Long id;

    @Column
    @NotEmpty
    private String name;

    @Column
    @NotEmpty
    private String lastname;


    @NotEmpty
    @Pattern(regexp = ".+@acme.com$")
    @Column(name = "email")
    public String email;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @NotNull
    @Size(min = 12, message = "The password length must be at least 12 chars!")
    private String password;

    @ElementCollection
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "email"))
    @Transient
    private List<String> roles = new ArrayList<>();


    @JsonIgnore
    private boolean locked = false;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String name, String lastname, String email, String password, List<String> roles) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.roles = roles;
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
        return email.toLowerCase();
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
