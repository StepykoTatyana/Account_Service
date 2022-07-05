package com.example.Account_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {


    // Acquiring the builder
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/api/auth/signup").permitAll()
                .mvcMatchers("/api/auth/changepass").hasAnyRole("Accountant", "USER", "Administrator")
                .mvcMatchers("/api/empl/payment").hasAnyRole("Accountant", "USER")
                .mvcMatchers("/api/acct/payments").hasAnyRole("Accountant")
                .mvcMatchers("/api/admin/user").hasAnyRole("Administrator")
                .mvcMatchers("/api/admin/user/role").hasAnyRole("Administrator")
                .and().httpBasic().and().csrf().disable();
    }
    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
