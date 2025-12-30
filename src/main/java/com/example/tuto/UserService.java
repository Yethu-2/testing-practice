package com.example.tuto;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing users
 * This will be used to demonstrate Spring service testing
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name, String email) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }

        User user = new User(name, email);
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public int getUserCount() {
        return Math.toIntExact(userRepository.count());
    }

    public Optional<User> updateUser(Long id, String name, String email) {
        Optional<User> userOptional = findById(id);

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        if (name != null && !name.isBlank()) {
            user.setName(name);
        }
        if (email != null && email.contains("@")) {
            user.setEmail(email);
        } else if (email != null) {
            throw new IllegalArgumentException("Invalid email");
        }

        User saved = userRepository.save(user);
        return Optional.of(saved);
    }

    public void clearAll() {
        userRepository.deleteAll();
    }
}
