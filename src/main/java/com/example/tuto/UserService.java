package com.example.tuto;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing users
 * This will be used to demonstrate Spring service testing
 */
@Service
public class UserService {

    private final List<User> users = new ArrayList<>();
    private long nextId = 1;

    public User createUser(String name, String email) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }

        User user = new User(nextId++, name, email);
        users.add(user);
        return user;
    }

    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public boolean deleteUser(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }

    public int getUserCount() {
        return users.size();
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

        return Optional.of(user);
    }

    public void clearAll() {
        users.clear();
    }
}
