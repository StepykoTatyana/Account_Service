package com.example.Account_Service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends CrudRepository<User, String> {
    @Query(value = "Select top 1 * from users where email=?1", nativeQuery = true)
    User findByEmail(@Param("email") String email);

    @Query(value = "Select  * from users", nativeQuery = true)
    List<User> selectAllUsers();

}
