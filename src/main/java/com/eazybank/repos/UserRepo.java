package com.eazybank.repos;

import com.eazybank.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findUsersByEmail(String email);
    Optional<Users> findByUsername(String username);
    Optional<Users> findUsersByEmailOrUsername(String email, String username);
}
