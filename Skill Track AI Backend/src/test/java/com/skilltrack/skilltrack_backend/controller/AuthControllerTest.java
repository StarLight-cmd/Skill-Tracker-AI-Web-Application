package com.skilltrack.skilltrack_backend.controller;

import com.skilltrack.skilltrack_backend.dto.LoginRequest;
import com.skilltrack.skilltrack_backend.model.User;
import com.skilltrack.skilltrack_backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldLoginSuccessfully() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setEmail("dev@gmail.com");
        request.setPassword("password123");

        User user = new User();
        user.setId(1L);
        user.setEmail("dev@gmail.com");
        user.setPassword("encodedPassword");

        Mockito.when(userService.getUserByEmail("dev@gmail.com"))
                .thenReturn(Optional.of(user));

        Mockito.when(passwordEncoder.matches("password123", "encodedPassword"))
                .thenReturn(true);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("dev@gmail.com"));
    }

    @Test
    void shouldReturn401WhenUserNotFound() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setEmail("unknown@gmail.com");
        request.setPassword("password123");

        Mockito.when(userService.getUserByEmail("unknown@gmail.com"))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }

    @Test
    void shouldReturn401WhenPasswordIncorrect() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setEmail("dev@gmail.com");
        request.setPassword("wrongPassword");

        User user = new User();
        user.setEmail("dev@gmail.com");
        user.setPassword("encodedPassword");

        Mockito.when(userService.getUserByEmail("dev@gmail.com"))
                .thenReturn(Optional.of(user));

        Mockito.when(passwordEncoder.matches("wrongPassword", "encodedPassword"))
                .thenReturn(false);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }
}
