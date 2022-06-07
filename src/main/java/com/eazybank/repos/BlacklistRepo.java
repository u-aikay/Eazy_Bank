package com.eazybank.repos;

import com.eazybank.model.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRepo extends JpaRepository<BlacklistToken, Long> {
    boolean existsByToken(String token);
}
