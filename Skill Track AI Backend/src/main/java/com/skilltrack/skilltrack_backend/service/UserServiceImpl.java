package com.skilltrack.skilltrack_backend.service;

import com.skilltrack.skilltrack_backend.exception.EmailAlreadyExistsException;
import com.skilltrack.skilltrack_backend.exception.ResourceNotFoundException;
import com.skilltrack.skilltrack_backend.model.User;
import com.skilltrack.skilltrack_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        String hashedPaassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPaassword);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(
                userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"))
        );
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return  userRepository.findAll();
    }
}
