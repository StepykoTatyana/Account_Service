package com.example.Account_Service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PaymentsRepository extends CrudRepository<Payment, String> {

    @Query(value = "Select * from payments where employee=?1", nativeQuery = true)
    List<Payment> findByEmailPayment(@Param("employee") String employee);
}