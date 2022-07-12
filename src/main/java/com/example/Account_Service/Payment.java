package com.example.Account_Service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@Entity
@Table(name = "payments")
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployee() {
        return employee.toLowerCase();
    }

    public void setEmployee(String employee) {
        this.employee = employee.toLowerCase();
    }


    @JoinColumn(referencedColumnName = "email", nullable = false)
    @JsonProperty("employee")
    private String employee;


    @Column
    @Pattern(regexp = "((0[1-9])|(1[0-2]))-2021", message = "Wrong date!")
    private String period;


    @Column
    @Min(value = 1, message = "Salary must be non negative!")
    private Long salary;




//    private User user;
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }


//    public String getEmployee() {
//        return employee.toLowerCase();
//    }
//
//    public void setEmployee(String employee) {
//        this.employee = employee.toLowerCase();
//    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
