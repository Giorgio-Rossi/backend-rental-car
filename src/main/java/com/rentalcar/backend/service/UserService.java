package com.rentalcar.backend.service;

import com.rentalcar.backend.dto.AdminUpdateDTO;
import com.rentalcar.backend.repository.CarRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.rentalcar.backend.repository.UserRepository;
import com.rentalcar.backend.dto.UserDTO;
import com.rentalcar.backend.model.User;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CarRequestRepository carRequestRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, CarRequestRepository carRequestRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.carRequestRepository = carRequestRepository;
        this.passwordEncoder = passwordEncoder;
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

    public UserDTO saveUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return convertToDTO(userRepository.save(user));
    }

    public void adminUpdateUser(Long userId, AdminUpdateDTO adminUpdateDTO) {
        User user = userRepository.findById(userId).orElse(null);
        if(user != null) {
            user.setRole(adminUpdateDTO.getRole());
            user.setFullName(adminUpdateDTO.getFullName());
            userRepository.save(user);
        }
    }

    public void adminUpdateUser1(Long userId, AdminUpdateDTO adminUpdateDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new RuntimeException(""));
            user.setRole(adminUpdateDTO.getRole());
            user.setFullName(adminUpdateDTO.getFullName());
            userRepository.save(user);

    }

    public void adminUpdateUser2(Long userId, AdminUpdateDTO adminUpdateDTO) {
        userRepository.findById(userId).ifPresent(getUserConsumer(adminUpdateDTO));

    }

    private Consumer<User> getUserConsumer(AdminUpdateDTO adminUpdateDTO) {
        return user1 -> {
            user1.setRole(adminUpdateDTO.getRole());
            user1.setFullName(adminUpdateDTO.getFullName());
            userRepository.save(user1);
        };
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

    public UserDTO updateUser(UserDTO updateUserDTO) {
        return userRepository.findById(updateUserDTO.getId()).map(user -> {
            if (updateUserDTO.getPassword() != null && !passwordEncoder.matches(updateUserDTO.getPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
            }
            user.setEmail(updateUserDTO.getEmail());
            user.setRole(updateUserDTO.getRole());
            user.setUsername(updateUserDTO.getUsername());
            user.setFullName(updateUserDTO.getFullName());
            return convertToDTO(userRepository.save(user));
        }).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    private UserDTO convertToDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), null, user.getFullName());
    }

    private User convertToEntity(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getUsername(), userDTO.getEmail(), userDTO.getRole(), userDTO.getPassword(), userDTO.getFullName());
    }




}
