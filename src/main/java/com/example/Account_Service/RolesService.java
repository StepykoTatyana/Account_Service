package com.example.Account_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
public class RolesService {
    Map<String, String> listRoles = Map.of("ACCOUNTANT", "ROLE_ACCOUNTANT",
            "USER", "ROLE_USER", "ADMINISTRATOR", "ROLE_ADMINISTRATOR",
            "AUDITOR", "ROLE_AUDITOR");

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    LogRepository logRepository;


    public ResponseEntity<User> addRoles(User user, Roles roles, UserDetails details) {
        List<String> listS = rolesRepository.findByEmail(roles.getUser());
        String newRole = listRoles.get(roles.getRole());
        if ((
                listS.contains(listRoles.get("ADMINISTRATOR"))
                        & (roles.getRole().equals("ACCOUNTANT") | roles.getRole().equals("USER") | roles.getRole().equals("AUDITOR")))
                |
                ((listS.contains(listRoles.get("ACCOUNTANT")) | listS.contains(listRoles.get("USER")) | listS.contains(listRoles.get("AUDITOR")))
                        & roles.getRole().equals("ADMINISTRATOR"))
        ) {
            throw new UserExistException("The user cannot combine administrative and business roles!");
        }
        if (listRoles.containsKey(roles.getRole())) {
            if (rolesRepository.findByRole(roles.getUser(), newRole) == null) {
                roles.setRole(newRole);
                rolesRepository.save(roles);
                List<String> listRoles = rolesRepository.findByEmail(roles.getUser());
                listRoles.sort(Comparator.naturalOrder());
                user.setRoles(listRoles);
                addLog("GRANT_ROLE", details.getUsername(),
                        "Grant role " + roles.getRole().replace("ROLE_", "") + " to " + user.getEmail(), "/api/admin/user/role");
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                throw new UserExistException("The user already have this role!");
            }
        } else {
            throw new UserNotExistException("Role not found!");
        }
    }

    public ResponseEntity<User> removeRoles(User user, Roles roles, UserDetails details) {
        String newRole = listRoles.get(roles.getRole());
        List<String> listS = rolesRepository.findByEmail(roles.getUser());
        if (listRoles.containsKey(roles.getRole())) {
            if (rolesRepository.findByRole(roles.getUser(), newRole) != null) {
                if (!roles.getRole().equals("ADMINISTRATOR")) {
                    if (listS.size() > 1) {
                        rolesRepository.deleteByEmailAndRole(roles.getUser(), newRole);
                        user.setRoles(rolesRepository.findByEmail(roles.getUser()));
                        addLog("REMOVE_ROLE", details.getUsername(),
                                "Remove role " + roles.getRole().replace("ROLE_", "") + " from " + user.getEmail(), "/api/admin/user/role");
                        return new ResponseEntity<>(user, HttpStatus.OK);
                    } else {
                        throw new UserExistException("The user must have at least one role!");
                    }
                } else {
                    throw new UserExistException("Can't remove ADMINISTRATOR role!");
                }
            } else {
                throw new UserExistException("The user does not have a role!");
            }

        } else {
            throw new UserNotExistException("Role not found!");
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
}
