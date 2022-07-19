package com.example.Account_Service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    private final LoggingController log;
    private final AuthController authController;

    public Runner(LoggingController log, AuthController authController) {
        this.log = log;
        this.authController = authController;
    }

    @Override
    public void run(String... args) throws InterruptedException {
        this.log.log();
        //this.authController.PostApiSignup(null);
    }
}