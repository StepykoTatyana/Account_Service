//package com.example.Account_Service;
//
//import org.hibernate.annotations.CreationTimestamp;
//
//import javax.persistence.*;
//import java.security.Timestamp;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "secureTokens")
//public class SecureToken{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true)
//    private String token;
//
//    @CreationTimestamp
//    @Column(updatable = false)
//    private Timestamp timeStamp;
//
//    @Column(updatable = false)
//    @Basic(optional = false)
//    private LocalDateTime expireAt;
//
//    @ManyToOne
//    @JoinColumn(name = "customer_id", referencedColumnName ="id")
//    private User user;
//
//    @Transient
//    private boolean isExpired;
//
//    public boolean isExpired() {
//        return getExpireAt().isBefore(LocalDateTime.now()); // this is generic implementation, you can always make it timezone specific
//    }
//    //getter an setter
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public Timestamp getTimeStamp() {
//        return timeStamp;
//    }
//
//    public void setTimeStamp(Timestamp timeStamp) {
//        this.timeStamp = timeStamp;
//    }
//
//    public LocalDateTime getExpireAt() {
//        return expireAt;
//    }
//
//    public void setExpireAt(LocalDateTime expireAt) {
//        this.expireAt = expireAt;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public void setExpired(boolean expired) {
//        isExpired = expired;
//    }
//}