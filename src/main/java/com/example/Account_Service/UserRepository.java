package com.example.Account_Service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends CrudRepository<Users, String> {
    @Query(value = "Select top 1 * from users where email=?1", nativeQuery = true)
    Users findByEmail(@Param("email") String email);
}
