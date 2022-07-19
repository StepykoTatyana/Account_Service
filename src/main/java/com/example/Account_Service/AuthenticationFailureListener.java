package com.example.Account_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Component
public class AuthenticationFailureListener implements
        ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private Log log;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    RolesRepository rolesRepository;


    public static HashMap<String, Integer> mapFailAuth = new HashMap<>();

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            String s = userDetailsService.getEmailAuth().toLowerCase();
            User user = userRepository.findByEmail(s);
            if (user!=null){
                if (!user.isLocked()) {
                    Log log = new Log();
                    log.setPath(request.getServletPath());
                    log.setObject(request.getServletPath());
                    log.setAction("LOGIN_FAILED");
                    log.setSubject(s);
                    logRepository.save(log);
                    if (mapFailAuth.containsKey(s)) {
                        mapFailAuth.put(s, mapFailAuth.get(s) + 1);
                    } else {
                        mapFailAuth.put(s, 1);
                    }
                    if (mapFailAuth.get(s) == 5) {
                        Log log1 = new Log();
                        //user.setLocked(true);
                        log1.setPath(request.getServletPath());
                        log1.setObject(request.getServletPath());
                        log1.setAction("BRUTE_FORCE");
                        log1.setSubject(s);
                        logRepository.save(log1);
                        userRepository.save(user);

                        Log log2 = new Log();
                        if (!rolesRepository.findByEmail(s.toLowerCase()).contains("ROLE_ADMINISTRATOR")){
                            user.setLocked(true);
                            log2.setPath(request.getServletPath());
                            log2.setObject("Lock user " + s);
                            log2.setAction("LOCK_USER");
                            log2.setSubject(s);
                            logRepository.save(log2);
                            userRepository.save(user);
                        }

                    }
                }
            } else {
                Log log = new Log();
                log.setPath(request.getServletPath());
                log.setObject(request.getServletPath());
                log.setAction("LOGIN_FAILED");
                log.setSubject(s);
                logRepository.save(log);
            }
        }
    }
}