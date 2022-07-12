//package com.example.Account_Service.PutTask;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
//@RestController
//public class EmployeeController {
//    private ConcurrentMap<Long, Employee> employeeMap = new ConcurrentHashMap<>();
//
//    @PutMapping("/employees/{id}")
//    public Employee updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
//        employeeMap.put(id, employee);
//        return employee;
//    }
//
//    @GetMapping("/employees/{id}")
//    public Employee getEmployee(@PathVariable long id) {
//        return employeeMap.get(id);
//    }
//}