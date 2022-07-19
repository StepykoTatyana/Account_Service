//package com.example.Account_Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.LockedException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//@Component
//public class CustomAuthenticationExcHandler extends SimpleUrlAuthenticationFailureHandler {
//
//    @Autowired
//    private UserDetailsServiceImpl userService;
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//                                        AuthenticationException exception) throws IOException, ServletException {
//        String email = request.getParameter("email");
//        User user = userRepository.findByEmail(userService.getEmailAuth());
//
//        if (user != null) {
//            if (user.isLocked()) {
//                exception = new LockedException("Your account has been locked due to 3 failed attempts."
//                        + " It will be unlocked after 24 hours.");
//            }
//        }
//
////        super.setDefaultFailureUrl("/login?error");
//        super.onAuthenticationFailure(request, response, exception);
//    }
//
//}
//
////
////    @ExceptionHandler(AuthenticationException.class)
////    public ResponseEntity<CustomErrorMessageUsers> exists(
////            AuthenticationException e, HttpServletRequest request) {
////
////        CustomErrorMessageUsers body = new CustomErrorMessageUsers(
////                LocalDateTime.now().toString(),
////                HttpStatus.UNAUTHORIZED.value(),
////                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
////                "dfffffffffffffffffffffffffffffffff",
////                request.getServletPath());
////
////        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
////    }
//
////    public void handle(HttpServletRequest req,
////                       HttpServletResponse res,
////                       AuthenticationFailureException authenticationException,
////                       LogRepository logRepository)
////            throws IOException {
////
////        System.out.println("5555555555555555555555555555555");
////        ObjectMapper mapper = new ObjectMapper();
////        CustomErrorMessageUsers jsonString = new CustomErrorMessageUsers();
////
////        jsonString.setTimestamp(LocalDateTime.now().toString());
////        jsonString.setStatus(HttpStatus.UNAUTHORIZED.value());
////        jsonString.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
////        jsonString.setMessage("UNAUTHORIZED");
////        jsonString.setPath(req.getServletPath());
////
////        String json = mapper.writeValueAsString(jsonString);
////
////
////        res.setContentType("application/json;charset=UTF-8");
////        res.setStatus(403);
////        res.getWriter().write(json);
////
////        Log log = new Log();
////        log.setPath(req.getServletPath());
////        log.setObject(req.getServletPath());
////        log.setAction("ACCESS_DENIED");
////        log.setSubject(req.getRemoteUser());
////        logRepository.save(log);
////
////    }
//
