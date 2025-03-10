package com.rentalcar.backend.repository;

import com.rentalcar.backend.model.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Long> {
    boolean existsByToken(String token);
}
