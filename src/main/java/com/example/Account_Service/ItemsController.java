package com.example.Account_Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class ItemsController {
    public static HashMap<String, Users> usersHashMap = new HashMap<>();

    @PostMapping("api/auth/signup")
    public ResponseEntity<?> PostApi(@Validated @RequestBody Users userFromPost) {
        Users user = new Users();
        user.setName(userFromPost.getName());
        user.setLastname(userFromPost.getLastname());
        user.setEmail(userFromPost.getEmail());
        user.setPassword(userFromPost.getPassword());

        usersHashMap.put(user.getEmail(), user);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

}