package com.skilltrack.skilltrack_backend.Service;

import com.skilltrack.skilltrack_backend.exception.EmailAlreadyExistsException;
import com.skilltrack.skilltrack_backend.exception.ResourceNotFoundException;
import com.skilltrack.skilltrack_backend.model.User;
import com.skilltrack.skilltrack_backend.repository.UserRepository;
import com.skilltrack.skilltrack_backend.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldCreateUserWithEncodedPassword() {

        User user = new User();
        user.setEmail("dev@gmail.com");
        user.setPassword("plainPassword");

        when(userRepository.existsByEmail("dev@gmail.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("plainPassword"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User saved = userService.createUser(user);

        assertEquals("encodedPassword", saved.getPassword());
        verify(passwordEncoder).encode("plainPassword");
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {

        User user = new User();
        user.setEmail("dev@gmail.com");

        when(userRepository.existsByEmail("dev@gmail.com"))
                .thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.createUser(user));

        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void shouldReturnUserById() {

        User user = new User();
        user.setId(1L);
        user.setEmail("dev@gmail.com");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("dev@gmail.com", result.get().getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getUserById(1L));
    }

    @Test
    void shouldReturnUserByEmail() {

        User user = new User();
        user.setEmail("dev@gmail.com");

        when(userRepository.findByEmail("dev@gmail.com"))
                .thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByEmail("dev@gmail.com");

        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnAllUsers() {

        when(userRepository.findAll())
                .thenReturn(List.of(new User(), new User()));

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
    }
}

