package com.example.tuto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * STEP 2: Testing a Service Class
 * This demonstrates unit testing for business logic using JPA repository
 */
@DataJpaTest
@Import(UserService.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a user successfully")
    void testCreateUser() {
        // Given
        String name = "John Doe";
        String email = "john@example.com";

        // When
        User user = userService.createUser(name, email);

        // Then
        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertNotNull(user.getId());
    }

    @Test
    @DisplayName("Should throw exception when creating user with empty name")
    void testCreateUserWithEmptyName() {
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> userService.createUser("", "john@example.com")
        );
        
        assertEquals("Name cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when creating user with invalid email")
    void testCreateUserWithInvalidEmail() {
        assertThrows(
            IllegalArgumentException.class,
            () -> userService.createUser("John", "invalid-email")
        );
    }

    @Test
    @DisplayName("Should find user by ID")
    void testFindUserById() {
        // Create a user first
        User createdUser = userService.createUser("Jane Doe", "jane@example.com");
        
        // Find the user
        Optional<User> foundUser = userService.findById(createdUser.getId());
        
        // Verify
        assertTrue(foundUser.isPresent());
        assertEquals("Jane Doe", foundUser.get().getName());
    }

    @Test
    @DisplayName("Should return empty when user not found")
    void testFindUserByIdNotFound() {
        Optional<User> foundUser = userService.findById(999L);
        
        assertFalse(foundUser.isPresent());
        assertTrue(foundUser.isEmpty());
    }

    @Test
    @DisplayName("Should get all users")
    void testGetAllUsers() {
        // Create multiple users
        userService.createUser("User 1", "user1@example.com");
        userService.createUser("User 2", "user2@example.com");
        userService.createUser("User 3", "user3@example.com");
        
        // Get all users
        List<User> users = userService.getAllUsers();
        
        // Verify
        assertEquals(3, users.size());
        assertEquals(3, userService.getUserCount());
    }

    @Test
    @DisplayName("Should delete user successfully")
    void testDeleteUser() {
        // Create a user
        User user = userService.createUser("To Delete", "delete@example.com");
        assertEquals(1, userService.getUserCount());
        
        // Delete the user
        boolean deleted = userService.deleteUser(user.getId());
        
        // Verify
        assertTrue(deleted);
        assertEquals(0, userService.getUserCount());
        assertFalse(userService.findById(user.getId()).isPresent());
    }

    @Test
    @DisplayName("Should return false when deleting non-existent user")
    void testDeleteNonExistentUser() {
        boolean deleted = userService.deleteUser(999L);
        assertFalse(deleted);
    }

    @Test
    @DisplayName("Should auto-increment user IDs")
    void testUserIdAutoIncrement() {
        User user1 = userService.createUser("User 1", "user1@example.com");
        User user2 = userService.createUser("User 2", "user2@example.com");

        assertNotNull(user1.getId());
        assertNotNull(user2.getId());
        assertNotEquals(user1.getId(), user2.getId());
    }

    @Test
    @DisplayName("Should handle multiple operations correctly")
    void testMultipleOperations() {
        // Create users
        User user1 = userService.createUser("Alice", "alice@example.com");
        User user2 = userService.createUser("Bob", "bob@example.com");
        User user3 = userService.createUser("Charlie", "charlie@example.com");
        
        assertEquals(3, userService.getUserCount());
        
        // Delete one user
        userService.deleteUser(user2.getId());
        assertEquals(2, userService.getUserCount());
        
        // Verify remaining users
        assertTrue(userService.findById(user1.getId()).isPresent());
        assertFalse(userService.findById(user2.getId()).isPresent());
        assertTrue(userService.findById(user3.getId()).isPresent());
    }

    // ========== UPDATE (U in CRUD) ==========

    @Test
    @DisplayName("Should update user successfully")
    void testUpdateUser() {
        // Create a user
        User user = userService.createUser("Old Name", "old@example.com");
        Long userId = user.getId();
        
        // Update the user
        Optional<User> updatedUser = userService.updateUser(userId, "New Name", "new@example.com");
        
        // Verify
        assertTrue(updatedUser.isPresent());
        assertEquals("New Name", updatedUser.get().getName());
        assertEquals("new@example.com", updatedUser.get().getEmail());
        
        // Verify the change persisted
        Optional<User> fetchedUser = userService.findById(userId);
        assertTrue(fetchedUser.isPresent());
        assertEquals("New Name", fetchedUser.get().getName());
    }

    @Test
    @DisplayName("Should update only name when email is null")
    void testUpdateUserNameOnly() {
        User user = userService.createUser("John", "john@example.com");
        
        Optional<User> updated = userService.updateUser(user.getId(), "Johnny", null);
        
        assertTrue(updated.isPresent());
        assertEquals("Johnny", updated.get().getName());
        assertEquals("john@example.com", updated.get().getEmail()); // Email unchanged
    }

    @Test
    @DisplayName("Should update only email when name is null")
    void testUpdateUserEmailOnly() {
        User user = userService.createUser("John", "john@example.com");
        
        Optional<User> updated = userService.updateUser(user.getId(), null, "johnny@example.com");
        
        assertTrue(updated.isPresent());
        assertEquals("John", updated.get().getName()); // Name unchanged
        assertEquals("johnny@example.com", updated.get().getEmail());
    }

    @Test
    @DisplayName("Should return empty when updating non-existent user")
    void testUpdateNonExistentUser() {
        Optional<User> updated = userService.updateUser(999L, "Name", "email@example.com");
        
        assertFalse(updated.isPresent());
    }

    @Test
    @DisplayName("Should throw exception when updating with invalid email")
    void testUpdateUserWithInvalidEmail() {
        User user = userService.createUser("John", "john@example.com");
        
        assertThrows(
            IllegalArgumentException.class,
            () -> userService.updateUser(user.getId(), "John", "invalid-email")
        );
    }

    @Test
    @DisplayName("Should not update when name is blank")
    void testUpdateUserWithBlankName() {
        User user = userService.createUser("John", "john@example.com");
        String originalName = user.getName();
        
        userService.updateUser(user.getId(), "", "newemail@example.com");
        
        // Name should remain unchanged
        assertEquals(originalName, user.getName());
        // But email should be updated
        assertEquals("newemail@example.com", user.getEmail());
    }

    // ========== COMPLETE CRUD WORKFLOW TEST ==========

    @Test
    @DisplayName("Should perform complete CRUD operations workflow")
    void testCompleteCRUDWorkflow() {
        // CREATE
        User user = userService.createUser("Alice", "alice@example.com");
        assertNotNull(user.getId());
        assertEquals("Alice", user.getName());
        
        // READ
        Optional<User> foundUser = userService.findById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals("alice@example.com", foundUser.get().getEmail());
        
        // UPDATE
        Optional<User> updatedUser = userService.updateUser(
            user.getId(), 
            "Alice Smith", 
            "alice.smith@example.com"
        );
        assertTrue(updatedUser.isPresent());
        assertEquals("Alice Smith", updatedUser.get().getName());
        assertEquals("alice.smith@example.com", updatedUser.get().getEmail());
        
        // DELETE
        boolean deleted = userService.deleteUser(user.getId());
        assertTrue(deleted);
        
        // VERIFY DELETION
        Optional<User> deletedUser = userService.findById(user.getId());
        assertFalse(deletedUser.isPresent());
    }
}
