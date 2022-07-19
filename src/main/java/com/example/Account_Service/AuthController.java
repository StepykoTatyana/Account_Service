package com.example.Account_Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
@Component
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    public void log() {
        LOGGER.info("This is an INFO level log message!");
    }

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesService rolesService;


    @Autowired
    LogRepository logRepository;

    static public List<String> breachedPasswords =
            Arrays.asList("PasswordForJanuary", "PasswordForFebruary",
                    "PasswordForMarch", "PasswordForApril", "PasswordForMay",
                    "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
                    "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");


    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> PostApiSignup(@Validated @Valid @RequestBody User userFromPost) {
        if (userFromPost == null) {
            LOGGER.error("User is null!");
            throw new UserExistException("User is null!!!");
        }
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
                    LOGGER.error("User exist!");
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

                    addLogs(null, user, "CREATE_USER", "/api/auth/signup", user.getEmail());

                    LOGGER.info("user has been added successfully");
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
                    addLogs(details, userFromBd, "CHANGE_PASSWORD", "/api/auth/changepass", userFromBd.getEmail());
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
                    return rolesService.addRoles(user, mapRoles, details);
                case REMOVE:
                    return rolesService.removeRoles(user, mapRoles, details);

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

        User user = userRepository.findByEmail(email.toLowerCase());
        List<String> listRoles = rolesService.rolesRepository.findByEmail(email.toLowerCase());
        if (!listRoles.contains("ROLE_ADMINISTRATOR")) {
            if (user != null) {
                userRepository.delete(user);
                for (String s : listRoles) {
                    rolesService.rolesRepository.deleteByEmailAndRole(email.toLowerCase(), s);
                }
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                map.put("user", email);
                map.put("status", "Deleted successfully!");
                addLogs(details, user, "DELETE_USER", "/api/admin/user", email.toLowerCase());
                return new ResponseEntity<>(map, HttpStatus.OK);
            } else {
                throw new UserNotExistException("User not found!");
            }
        } else {
            throw new UserExistException("Can't remove ADMINISTRATOR role!");
        }

    }


    public void addLogs(UserDetails details, User user, String message, String path, String object) {
        Log log = new Log();
        log.setAction(message);
        if (details == null) {
            log.setSubject("Anonymous");
        } else {
            log.setSubject(details.getUsername());
        }
        log.setObject(object);
        log.setPath(path);
        logRepository.save(log);
    }
}