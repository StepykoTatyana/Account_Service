package com.example.Account_Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String password;
    private final List<GrantedAuthority> rolesAndAuthorities;

    public List<GrantedAuthority> getRolesAndAuthorities() {
        return rolesAndAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return rolesAndAuthorities;
    }


    public UserDetailsImpl(User user) {
        System.out.println(user.getEmail().toLowerCase());
        System.out.println(user.getPassword());
        System.out.println(user.getRoles().get(0));

        username = user.getEmail().toLowerCase();
        password = user.getPassword();
        rolesAndAuthorities = user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        System.out.println(rolesAndAuthorities);
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 4 remaining methods that just return true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}