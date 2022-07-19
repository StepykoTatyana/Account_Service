package com.example.Account_Service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public interface LogRepository extends CrudRepository<Log, Long> {
    @Query(value = "Select * from log", nativeQuery = true)
    List<Log> allLog();

}
