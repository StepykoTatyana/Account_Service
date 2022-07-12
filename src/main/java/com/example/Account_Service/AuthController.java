package com.example.Account_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController

public class AuthController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
//
//    @Autowired
//    NewPassword newPassword;

    static public User userDefault = new User("John", "Doe", "JohnDoe@acme.com", "secret");

    static public List<String> breachedPasswords =
            Arrays.asList("PasswordForJanuary", "PasswordForFebruary",
                    "PasswordForMarch", "PasswordForApril", "PasswordForMay",
                    "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
                    "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");


    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> PostApiSignup(@Validated @Valid @RequestBody User userFromPost) {
        if (breachedPasswords.contains(userFromPost.getPassword())) {
            throw new UserExistException("The password is in the hacker's database!");
        } else {
            User user = new User();

            if (breachedPasswords.contains(userFromPost.getPassword())) {
                throw new UserExistException("The password is in the hacker's database!");
            } else {
                user.setName(userFromPost.getName());
                user.setLastname(userFromPost.getLastname());
                user.setEmail(userFromPost.getEmail().toLowerCase());
                System.out.println(userFromPost.getPassword());
                user.setPassword(encoder.encode(userFromPost.getPassword()));
                if (userDetailsService.userRepo.findByEmail(userFromPost.getEmail().toLowerCase()) != null) {
                    throw new UserExistException("User exist!");
                } else {
                    user.setPassword(encoder.encode(userFromPost.getPassword()));
                    userDetailsService.userRepo.save(user);
                    return new ResponseEntity<>(user, HttpStatus.OK);
                }
            }
        }
    }


    @PostMapping("/api/auth/changepass")
    public ResponseEntity<?> PostChangePassword(@AuthenticationPrincipal UserDetails details,
                                                @Validated @Valid @RequestBody NewPassword newPassword) {
        if (breachedPasswords.contains(newPassword.getNew_password())) {
            throw new UserExistException("The password is in the hacker's database!");
        } else {
            if (userDetailsService.userRepo.findByEmail(details.getUsername().toLowerCase()) != null) {
                User userFromBd = userDetailsService.userRepo.findByEmail(details.getUsername());
                if (!encoder.matches(newPassword.getNew_password(), userFromBd.getPassword())) {
                    userFromBd.setPassword(encoder.encode(newPassword.getNew_password()));
                    userDetailsService.userRepo.save(userFromBd);
                    Map<String, String> map = new LinkedHashMap<>();
                    map.put("email", details.getUsername());
                    map.put("status", "The password has been updated successfully");
                    return new ResponseEntity<>(map, HttpStatus.OK);
                } else {
                    throw new UserExistException("The passwords must be different!");
                }
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }



}