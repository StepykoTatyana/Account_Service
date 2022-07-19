package com.example.Account_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepo;


    @Autowired
    RolesRepository repository;
    public String emailAuth;

    public String getEmailAuth() {
        return emailAuth;
    }

    public void setEmailAuth(String emailAuth) {
        this.emailAuth = emailAuth;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            emailAuth = email;
            User user = userRepo.findByEmail(email.toLowerCase());
            user.setRoles(repository.findByEmail(email.toLowerCase()));
            if(user.isLocked()){

                System.out.println("!!!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                throw new Exception();
            }else {
                return new UserDetailsImpl(user);
            }


        } catch (Exception e) {
            throw new UsernameNotFoundException("Not found: " + email);
        }
    }

}
