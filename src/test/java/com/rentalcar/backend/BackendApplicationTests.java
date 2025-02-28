package com.rentalcar.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.rentalcar.backend.dto.UserDTO;
import com.rentalcar.backend.model.User;
import com.rentalcar.backend.repository.UserRepository;
import com.rentalcar.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindUserById() {

		User user = new User(1L, "john_doe", "john@example.com", "USER", "password", "John Doe");
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		UserDTO foundUser = userService.getUserById(1L);
		assertNotNull(foundUser);
		assertEquals("john_doe", foundUser.getUsername());
	}
}
