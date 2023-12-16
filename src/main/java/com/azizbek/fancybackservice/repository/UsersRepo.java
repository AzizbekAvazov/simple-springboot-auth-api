package com.azizbek.fancybackservice.repository;

import com.azizbek.fancybackservice.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Azizbek Avazov on июнь, 2022
 */

@Repository
public interface UsersRepo extends CrudRepository<Users, Long> {
    @Query(value = "SELECT * FROM users WHERE email=:email", nativeQuery = true)
    Optional<Users> findByEmail(@Param(value = "email") String email);

    @Query(value = "SELECT * FROM users WHERE username=:username", nativeQuery = true)
    Users getUserByUsername(@Param(value = "username") String username);
}
