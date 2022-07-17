package com.example.Account_Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataLoader {
//
//    private final RolesRepository rolesRepository;
//
//    @Autowired
//    public DataLoader(RolesRepository rolesRepository) {
//        this.rolesRepository = rolesRepository;
//        createRoles();
//    }
//
//    private void createRoles() {
//        try {
//            rolesRepository.save(new Group("ROLE_ADMINISTRATOR"));
//            rolesRepository.save(new Group("ROLE_USER"));
//            rolesRepository.save(new Group("ROLE_ACCOUNTANT"));
//        } catch (Exception e) {
//
//        }
//    }
//}