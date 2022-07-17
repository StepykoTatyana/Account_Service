package com.example.Account_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint = new RestAuthenticationEntryPoint();

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService) // user store 1
                .passwordEncoder(getEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint) // Handle auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests() // manage access
                .antMatchers("/actuator/**").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/auth/signup", "/actuator/shutdown").permitAll()
                .mvcMatchers(HttpMethod.PUT, "/api/admin/user/role").hasAnyRole("ADMINISTRATOR")
                .mvcMatchers(HttpMethod.GET, "/api/admin/user").hasAnyRole("ADMINISTRATOR")
                .mvcMatchers(HttpMethod.DELETE, "/api/admin/user/**").hasAnyRole("ADMINISTRATOR")
                .antMatchers("/api/auth/changepass").fullyAuthenticated()
                .antMatchers(HttpMethod.GET, "/api/empl/payment").hasAnyRole("ACCOUNTANT", "USER")
                .antMatchers("/api/acct/payments").hasRole("ACCOUNTANT")
                .and().httpBasic().and().cors().disable().headers().frameOptions().disable().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no session;
                .and().exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    CustomAccessDeniedHandler defaultAccessDeniedHandler = new CustomAccessDeniedHandler();
                    defaultAccessDeniedHandler.handle(request, response, accessDeniedException);
                });
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(13);
    }
}
