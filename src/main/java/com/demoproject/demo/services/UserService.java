package com.demoproject.demo.services;

import com.demoproject.demo.entities.User;
import com.demoproject.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET ALL
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // GET BY ID
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // CREATE
    public User create(User user) {
        return userRepository.save(user);
    }

    // UPDATE
    public User update(Long id, User updated) {

        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setName(updated.getName());
        existing.setSurname(updated.getSurname());
        existing.setEmail(updated.getEmail());

        return userRepository.save(existing);
    }

    // DELETE
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}

