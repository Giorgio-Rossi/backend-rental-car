
package com.rentalcar.backend.repository;

import com.rentalcar.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>  findByUsername(String username);
    Optional<User> findTopByOrderByIdDesc();


}

