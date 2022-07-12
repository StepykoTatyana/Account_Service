package com.example.Account_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Validated
@RestController
public class UserController {
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    PaymentsRepository paymentsRepository;

//    @Autowired
//    ResponsePaymentGet responsePaymentGet;


//    @GetMapping("/api/empl/payment")
//    public ResponseEntity<?> getPayment(@AuthenticationPrincipal UserDetails details) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        User user = userDetailsService.userRepo.findByEmail(details.getUsername());
////        if (user == null) {
////            if (details.getUsername().equals(userDefault.getEmail())
////                    & details.getPassword().equals(userDefault.getPassword())) {
////                user = userDefault;
////                user.setPassword(encoder.encode(userDefault.getPassword()));
////            }
////        }
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }


    @PostMapping("/api/acct/payments")
    public ResponseEntity<?> postPayment(@RequestBody List<@Valid Payment> payments) {
        boolean foundPeriod = false;

        if (payments.stream().anyMatch(x -> userDetailsService.userRepo.findByEmail(x.getEmployee()) == null)) {
            throw new UserExistException("Unknown user!");
        } else {
            for (Payment payment : payments) {
                if (paymentsRepository.findByEmailPayment(payment.getEmployee()) != null) {
                    List<Payment> payments1 = paymentsRepository.findByEmailPayment(payment.getEmployee());
                    for (Payment payment1 : payments1) {
                        if (payment1.getPeriod().equalsIgnoreCase(payment.getPeriod())
                                & payment1.getEmployee().equalsIgnoreCase(payment.getEmployee())) {
                            foundPeriod = true;
                            break;
                        }
                    }
                }
                if (foundPeriod) {
                    break;
                }
                paymentsRepository.save(payment);
            }

            if (!foundPeriod) {
                return new ResponseEntity<>("{\"status\": \"Added successfully!\"}", HttpStatus.OK);
            } else {
                payments.forEach(x -> paymentsRepository.delete(x));
                throw new UserExistException("Error!");
            }
        }
    }


    @PutMapping("/api/acct/payments")
    public ResponseEntity<?> putPayment(@RequestBody @Validated Payment payment) {
        if (userDetailsService.userRepo.findByEmail(payment.getEmployee()) == null) {
            throw new UserExistException("Unknown user!");
        } else {
            List<Payment> payment1 = paymentsRepository.findByEmailPayment(payment.getEmployee()).stream()
                    .filter(x -> x.getPeriod().equalsIgnoreCase(payment.getPeriod())).collect(Collectors.toList());
            paymentsRepository.delete(payment1.get(0));
            paymentsRepository.save(payment);
            return new ResponseEntity<>("{\"status\": \"Updated successfully!\"}", HttpStatus.OK);

        }
    }


    @GetMapping("/api/empl/payment")
    public ResponseEntity<?> putPayment(@AuthenticationPrincipal UserDetails details,
                                        @RequestParam(required = false) String period) throws ParseException {

        List<ResponsePaymentGet> responsePaymentGets = new ArrayList<>();
        User user = userDetailsService.userRepo.findByEmail(details.getUsername());
        List<Payment> payments = paymentsRepository.findByEmailPayment(details.getUsername());
        if (userDetailsService.userRepo.findByEmail(details.getUsername().toLowerCase()) == null) {
            throw new UserExistException("Unknown user!");
        } else {
            for (Payment paymentFromBd : payments) {
                ResponsePaymentGet responsePaymentGet = new ResponsePaymentGet();
                responsePaymentGet.setName(user.getName());
                responsePaymentGet.setLastname(user.getLastname());
                List<String> listDate = List.of(paymentFromBd.getPeriod().split("-"));
                String m = String.valueOf(Month.of(Integer.parseInt(listDate.get(0))));
                String monthF = m.substring(0, 1).toUpperCase() + m.substring(1).toLowerCase();
                Year year = Year.of(Integer.parseInt(listDate.get(1)));
                String dateCorrect = monthF + "-" + year;
                responsePaymentGet.setPeriod(dateCorrect);
                String salary = paymentFromBd.getSalary() / 100 + " dollar(s) " + paymentFromBd.getSalary() % 100 + " cent(s)";
                responsePaymentGet.setSalary(salary);
                responsePaymentGets.add(responsePaymentGet);
                if (paymentFromBd.getPeriod().equalsIgnoreCase(period) & period != null) {
                    return new ResponseEntity<>(responsePaymentGet, HttpStatus.OK);
                }
            }
            if (period != null) {
                throw new UserExistException("Period is not exist!");
            } else {
                responsePaymentGets.sort(Comparator.comparing((x) ->
                        Month.valueOf(x.getPeriod().split("-")[0].toUpperCase()).getValue()));
                Collections.reverse(responsePaymentGets);
                return new ResponseEntity<>(responsePaymentGets, HttpStatus.OK);
            }


        }
    }
}
