package com.example.Account_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    RolesRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {

            User user = userRepo.findByEmail(email.toLowerCase());
            user.setRoles(repository.findByEmail(email.toLowerCase()));
            return new UserDetailsImpl(user);

        } catch (Exception e) {
            throw new UsernameNotFoundException("Not found: " + email);
        }
    }
}
