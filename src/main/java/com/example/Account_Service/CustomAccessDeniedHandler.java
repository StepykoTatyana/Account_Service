package com.example.Account_Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req,
                       HttpServletResponse res,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {


        ObjectMapper mapper = new ObjectMapper();
        CustomErrorMessageUsers jsonString = new CustomErrorMessageUsers();

        jsonString.setTimestamp(LocalDateTime.now().toString());
        jsonString.setStatus(HttpStatus.FORBIDDEN.value());
        jsonString.setError(HttpStatus.FORBIDDEN.getReasonPhrase());
        jsonString.setMessage("Access Denied!");
        jsonString.setPath(req.getServletPath());

        String json = mapper.writeValueAsString(jsonString);


        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(403);
        res.getWriter().write(json);


    }
}