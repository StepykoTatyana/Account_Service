package com.example.Account_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;

@Validated
@RestController
@Component
public class LoggingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingController.class);

    public void log() {
        LOGGER.info("This is an INFO level log message!");
    }


    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesService rolesService;

    @Autowired
    LogRepository logRepository;

    @PutMapping("/api/admin/user/access")
    public ResponseEntity<?> putAccessAdmin(@AuthenticationPrincipal UserDetails details,
                                            @Validated @Valid @RequestBody Roles roles) {
        User user = userRepository.findByEmail(roles.getUser());
        if (user != null) {
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            List<String> list = rolesService.rolesRepository.findByEmail(roles.getUser());
            if (list.contains("ROLE_ADMINISTRATOR")) {
                throw new UserExistException("Can't lock the ADMINISTRATOR!");
            } else {
                switch (roles.getOperation()) {
                    case LOCK:
                        addLog("LOCK_USER", details.getUsername(),
                                "Lock user " + user.getEmail(), "/api/admin/user/access");
                        user.setLocked(true);
                        map.put("status", "User " + user.getEmail() + " locked!");
                        userRepository.save(user);
                        return new ResponseEntity<>(map, HttpStatus.OK);

                    case UNLOCK:
                        addLog("UNLOCK_USER", details.getUsername(),
                                "Unlock user " + user.getEmail(), "/api/admin/user/access");
                        user.setLocked(false);
                        map.put("status", "User " + user.getEmail() + " unlocked!");
                        userRepository.save(user);
                        return new ResponseEntity<>(map, HttpStatus.OK);

                    default:
                        throw new UserNotExistException("user is not exist");
                }
            }
        } else {
            throw new UserNotExistException("User not found!");
        }
    }

    private void addLog(String action, String subject, String object, String path) {
        Log log = new Log();
        log.setPath(path);
        log.setObject(object);
        log.setAction(action);
        log.setSubject(subject);
        logRepository.save(log);

    }


    @GetMapping("/api/security/events")
    public ResponseEntity<?> getRoles(@AuthenticationPrincipal UserDetails details) {
        List<Log> list = logRepository.allLog();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}