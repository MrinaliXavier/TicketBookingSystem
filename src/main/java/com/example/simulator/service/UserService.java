package com.example.simulator.service;

import com.example.simulator.dto.UserDto;
import com.example.simulator.entity.User;
import com.example.simulator.exception.ResourceNotFoundException;
import com.example.simulator.exception.ValidationException;
import com.example.simulator.repository.UserRepository;
import com.example.simulator.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    //user Registration
    public User registerUser(UserDto userDto) {
        logger.info("Starting registration process for user with username: {}", userDto.getUsername());

        // Validate the UserDTO
        List<String> validationErrors = ValidationUtil.validateUserDTO(userDto);
        if (!validationErrors.isEmpty()) {
            logger.warn("Validation errors for username {}: {}", userDto.getUsername(), validationErrors);
            throw new ValidationException(validationErrors);
        }

        // Check for existing username, email, and phone number
        if (userRepository.existsByUsername(userDto.getUsername())) {
            logger.warn("Username {} is already registered.", userDto.getUsername());
            throw new RuntimeException("Username is already registered.");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            logger.warn("Email {} is already registered.", userDto.getEmail());
            throw new RuntimeException("Email is already registered.");
        }
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            logger.warn("Phone number {} is already registered.", userDto.getPhoneNumber());
            throw new RuntimeException("Phone number is already registered.");
        }

        // Create and save the User
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUserRole(userDto.getRole());

        User savedUser = userRepository.save(user);
        logger.info("Successfully registered user with ID: {}", savedUser.getId());

        return savedUser;

    }

    // Authenticate User's credentials
    public Authentication authenticateUser(String username, String password) {
        List<User> users = userRepository.findByUsername(username);

        if (users.isEmpty()) {
            logger.error("Invalid login attempt for user with username: {}", username);
            throw new BadCredentialsException("Invalid username or password");
        }

        // Assuming usernames are unique, pick the first user
        User user = users.get(0);

        if(!password.equals(user.getPassword())) {
            logger.error("Invalid login attempt for user with username: {}", username);
            throw new BadCredentialsException("Invalid password");
        }

        // Attach the user's role in the authentication object
        return new UsernamePasswordAuthenticationToken(user, null, List.of(new SimpleGrantedAuthority(user.getUserRole().name())));

    }

    // Get all user
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    // Update an existing user
    public User updateUser(Integer id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        userRepository.save(existingUser);
        return userRepository.save(existingUser);

    }

    // Delete User
    public boolean deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}



