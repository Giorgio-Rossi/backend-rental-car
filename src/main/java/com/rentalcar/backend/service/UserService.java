package com.rentalcar.backend.service;

import com.rentalcar.backend.repository.CarRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.rentalcar.backend.repository.UserRepository;
import com.rentalcar.backend.dto.UserDTO;
import com.rentalcar.backend.model.User;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CarRequestRepository carRequestRepository;

    /*
    private final BCryptPasswordEncoder passwordEncoder;
    */
    @Autowired
    public UserService(UserRepository userRepository, CarRequestRepository carRequestRepository) {
        this.userRepository = userRepository;
    /*    this.passwordEncoder = passwordEncoder;    */
        this.carRequestRepository = carRequestRepository;
    }


    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public UserDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }


    public UserDTO saveUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        return convertToDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato");
            }
            User user = userOpt.get();
            carRequestRepository.deleteByUserId(id);
            userRepository.delete(user);

        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato");
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Impossibile eliminare l'utente, potrebbe avere dati associati");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore durante l'eliminazione dell'utente");
        }
    }

    /*
    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new RuntimeException("User already exists!");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return saveUser(userDTO);
    }
    */


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
