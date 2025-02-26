package com.rentalcar.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.rentalcar.backend.repository.UserRepository;
import com.rentalcar.backend.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null); 
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User registerUser(Long id, String username, String email, String role, String password, String fullName){
        if (userRepository.findByEmail(email) != null){
            throw new RuntimeException("User already exists!");
        }

        String encryptedPassword = passwordEncoder.encode(password);

        User newUser = new User(id, username, email, role, encryptedPassword, fullName);

        return userRepository.save(newUser);
    }

    public User updateUser(Long id, User updateUser){
        return userRepository.findById(id).map( user -> {
            user.setEmail(updateUser.getEmail());
            user.setRole(updateUser.getRole());
            user.setUsername(updateUser.getUsername());
            user.setPassword(updateUser.getPassword());
            user.setFullName(updateUser.getFullName());
            return saveUser(user);
        }).orElseThrow(() -> new RuntimeException("User not found!"));

    }
}
