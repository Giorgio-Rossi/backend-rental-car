package com.rentalcar.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.rentalcar.backend.repository.UserRepository;
import com.rentalcar.backend.dto.UserDTO;
import com.rentalcar.backend.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public UserDTO getUserByUsername(String username) {
        return convertToDTO(userRepository.findByUsername(username));
    }

    public UserDTO getUserByEmail(String email) {
        return convertToDTO(userRepository.findByEmail(email));
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        return convertToDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new RuntimeException("User already exists!");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return saveUser(userDTO);
    }

    public UserDTO updateUser(UserDTO updateUserDTO) {
        return userRepository.findById(updateUserDTO.getId()).map(user -> {
            user.setEmail(updateUserDTO.getEmail());
            user.setRole(updateUserDTO.getRole());
            user.setUsername(updateUserDTO.getUsername());
            user.setPassword(updateUserDTO.getPassword());
            user.setFullName(updateUserDTO.getFullName());
            return convertToDTO(userRepository.save(user));
        }).orElseThrow(() -> new RuntimeException("User not found!"));
    }


    private UserDTO convertToDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getPassword(), user.getFullName());
    }

    private User convertToEntity(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getUsername(), userDTO.getEmail(), userDTO.getRole(), userDTO.getPassword(), userDTO.getFullName());
    }
}
