package com.example.tuto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * STEP 3: Integration Testing with Spring Context
 * This demonstrates testing with Spring's dependency injection
 * 
 * @SpringBootTest loads the complete Spring application context
 */
@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should inject UserService from Spring context")
    void testServiceInjection() {
        assertNotNull(userService, "UserService should be autowired");
    }

    @Test
    @DisplayName("Should create and retrieve user using Spring-managed service")
    void testCreateUserWithSpringContext() {
        userRepository.deleteAll();
        User user = userService.createUser("Spring User", "spring@example.com");
        
        assertNotNull(user);
        assertTrue(userService.findById(user.getId()).isPresent());
    }
}
