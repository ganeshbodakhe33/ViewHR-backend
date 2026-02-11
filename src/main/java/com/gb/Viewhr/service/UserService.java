package com.gb.Viewhr.service;

import com.gb.Viewhr.entity.User;
import com.gb.Viewhr.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,
                       PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User updateUser(Long id, User updated) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setRole(updated.getRole());
        existing.setManager(updated.getManager());
        existing.setStatus(updated.getStatus());

        return repository.save(existing);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}