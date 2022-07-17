package com.example.Account_Service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public interface RolesRepository extends CrudRepository<Roles, Long> {
    @Query(value = "Select top 1 * from roles where email=?1 and role=?2", nativeQuery = true)
    Roles findByRole(@Param("email") String email, @Param("role") String role);

    @Query(value = "Select role from roles where email=?1", nativeQuery = true)
    List<String> findByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "delete from roles where email=?1 and role=?2", nativeQuery = true)
    void deleteByEmailAndRole(@Param("email") String email,@Param("role") String role0);


}
