package com.janvi.reservation_system.service;

import com.janvi.reservation_system.entity.User;
import com.janvi.reservation_system.exception.DuplicateUsernameException;
import com.janvi.reservation_system.exception.ResourceNotFoundException;
import com.janvi.reservation_system.repository.UserRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateUsernameException("Username '" + user.getUsername() + "' already exists");
        }
        return userRepository.save(user);
    }

    // public User updateUser(Long id, User userDetails) {
    //     User user = userRepository.findById(id)
    //             .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    
    //     user.setUsername(userDetails.getUsername());
    //     user.setPassword(userDetails.getPassword());
    //     user.setEmail(userDetails.getEmail());
    //     user.setPhoneNumber(userDetails.getPhoneNumber());
    
    //     return userRepository.save(user);
    // }

    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found"));
    
        
        if (!existingUser.getUsername().equals(userDetails.getUsername())) {
            if (userRepository.existsByUsername(userDetails.getUsername())) {
                throw new RuntimeException("Username '" + userDetails.getUsername() + "' already exists");
            }
            existingUser.setUsername(userDetails.getUsername());
        }
    
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPassword(userDetails.getPassword());
        existingUser.setPhoneNumber(userDetails.getPhoneNumber());
    
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User with username '" + username + "' not found.");
        }
        return user.getId();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updatePassword(Long id, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    
    
}
