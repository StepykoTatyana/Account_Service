//package com.example.Account_Service;
//
//
//import javax.persistence.*;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "principle_groups")
//public class Group{
//
//    //removed getter and setter to save space
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true, nullable = false)
//    private String role;
//
//    public Group() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
////    @OneToMany(mappedBy = "userGroups")
////    private Set<User> users = new LinkedHashSet<>();
//
////    public void setUsers(Set<User> users) {
////        this.users = users;
////    }
////
////    public Set<User> getUsers() {
////        return users;
////    }
//
//    public Group(String role) {
//        this.role = role;
//    }
//}