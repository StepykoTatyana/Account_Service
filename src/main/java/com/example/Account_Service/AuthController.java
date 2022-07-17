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


    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesService rolesService;

    static public List<String> breachedPasswords =
            Arrays.asList("PasswordForJanuary", "PasswordForFebruary",
                    "PasswordForMarch", "PasswordForApril", "PasswordForMay",
                    "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
                    "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");


    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> PostApiSignup(@Validated @Valid @RequestBody User userFromPost) {
        Roles roles = new Roles();
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
                user.setPassword(encoder.encode(userFromPost.getPassword()));
                if (userDetailsService.userRepo.findByEmail(userFromPost.getEmail().toLowerCase()) != null) {
                    throw new UserExistException("User exist!");
                } else {
                    user.setPassword(encoder.encode(userFromPost.getPassword()));
                    if (userRepository.selectAllUsers().size() == 0) {
                        roles.setRole("ROLE_ADMINISTRATOR");
                    } else {
                        roles.setRole("ROLE_USER");
                    }
                    roles.setOperation(operation.GRANT);
                    roles.setUser(user.getEmail().toLowerCase());
                    rolesService.rolesRepository.save(roles);
                    user.setRoles(rolesService.rolesRepository.findByEmail(user.getEmail().toLowerCase()));
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
                User userFromBd = userDetailsService.userRepo.findByEmail(details.getUsername().toLowerCase());
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


    @PutMapping("/api/admin/user/role")
    public ResponseEntity<?> admitRoles(@AuthenticationPrincipal UserDetails details,
                                        @Validated @Valid @RequestBody Roles mapRoles) {
        User user = userRepository.findByEmail(mapRoles.getUser());
        if (user != null) {
            switch (mapRoles.getOperation()) {
                case GRANT:
                    return rolesService.addRoles(user, mapRoles);

                case REMOVE:
                    return rolesService.removeRoles(user, mapRoles);

                default:
                    throw new UserNotExistException("user is not exist");
            }
        } else {
            throw new UserNotExistException("User not found!");
        }


    }


    @GetMapping("/api/admin/user")
    public ResponseEntity<?> getRoles(@AuthenticationPrincipal UserDetails details) {
        List<User> list = userRepository.selectAllUsers();
        for (User user : list) {
            List<String> listRoles = rolesService.rolesRepository.findByEmail(user.getEmail().toLowerCase());
            listRoles.sort(Comparator.naturalOrder());
            user.setRoles(listRoles);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/user/{email}")
    public ResponseEntity<?> deleteUserByEmail(@AuthenticationPrincipal UserDetails details,
                                               @PathVariable String email) {

        User user = userRepository.findByEmail(email);
        List<String> listRoles = rolesService.rolesRepository.findByEmail(email);
        if (!listRoles.contains("ROLE_ADMINISTRATOR")) {
            if (user != null) {
                userRepository.delete(user);
                for (String s : listRoles) {
                    rolesService.rolesRepository.deleteByEmailAndRole(email, s);
                }
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                map.put("user", email);
                map.put("status", "Deleted successfully!");
                return new ResponseEntity<>(map, HttpStatus.OK);
            } else {
                throw new UserNotExistException("User not found!");
            }
        } else {
            throw new UserExistException("Can't remove ADMINISTRATOR role!");
        }

    }
}