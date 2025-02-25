
package com.rentalcar.backend.repository;

import com.rentalcar.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); 
    User findByEmail(String email);
}