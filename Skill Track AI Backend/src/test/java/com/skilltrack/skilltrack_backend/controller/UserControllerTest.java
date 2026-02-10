package com.skilltrack.skilltrack_backend.controller;

import com.skilltrack.skilltrack_backend.dto.CreateUserRequest;
import com.skilltrack.skilltrack_backend.model.User;
import com.skilltrack.skilltrack_backend.service.UserService;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUser() throws Exception{
        CreateUserRequest request = new CreateUserRequest();
        request.setFirstName("dev");
        request.setLastName("eloper");
        request.setEmail("dev@gmail.com");
        request.setPassword("12345678");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setFirstName("dev");
        savedUser.setLastName("eloper");
        savedUser.setEmail("dev@gmail.com");

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("dev@gmail.com"));
    }

    @Test
    void shouldReturnAllUsers() throws Exception{
        User user = new User();
        user.setId(1L);
        user.setFirstName("dev");
        user.setLastName("eloper");
        user.setEmail("dev@gmail.com");

        Mockito.when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("dev@gmail.com"));
    }

    @Test
    void shouldReturnUserById() throws Exception{
        User user = new User();
        user.setId(1L);
        user.setFirstName("dev");
        user.setEmail("dev@gmail.com");

        Mockito.when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("dev@gmail.com"));
    }

    @Test
    void shouldReturn404WhenUserNotFound()throws Exception{
        Mockito.when(userService.getUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/1")).andExpect(status().isNotFound());
    }

}
