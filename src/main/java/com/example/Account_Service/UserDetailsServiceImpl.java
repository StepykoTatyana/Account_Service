package com.example.Account_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.Account_Service.ItemsController.usersDefault;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {

            Users user = userRepo.findByEmail(email.toLowerCase());
            if (user == null) {
                if (email.equalsIgnoreCase(usersDefault.getEmail())) {
                    user = usersDefault;
                    user.setId(1L);
                }
            }
            return new UserDetailsImpl(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Not found: " + email);
        }
    }
}