package com.skilltrack.skilltrack_backend.controller;

import com.skilltrack.skilltrack_backend.dto.LoginRequest;
import com.skilltrack.skilltrack_backend.model.User;
import com.skilltrack.skilltrack_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        Optional<User> userOpt = userService.getUserByEmail(request.getEmail());

        if(userOpt.isEmpty()){
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        User user = userOpt.get();

        if(!passwordEncoder.matches(
                request.getPassword(),user.getPassword()
        )){
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        return ResponseEntity.ok(user);
    }
}
