package com.example.tuto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * STEP 6: Full Integration Testing with Real HTTP Calls
 * This tests the entire application stack including the web server
 * 
 * @SpringBootTest(webEnvironment = RANDOM_PORT) - Starts the full application
 * TestRestTemplate - Makes real HTTP calls to the running server
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private String baseUrl;

    @BeforeEach
    void setup() {
        baseUrl = "http://localhost:" + port + "/api/users";
        // Clear all users before each test using repository to reset state
        userRepository.deleteAll();
    }

    // ========== CREATE Integration Tests ==========

    @Test
    @DisplayName("Integration: Should create user via HTTP POST")
    void testCreateUserIntegration() {
        CreateUserRequest request = new CreateUserRequest("John Doe", "john@example.com");

        ResponseEntity<User> response = restTemplate.postForEntity(
            baseUrl,
            request,
            User.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals("john@example.com", response.getBody().getEmail());
        assertNotNull(response.getBody().getId());
    }

    // ========== READ Integration Tests ==========

    @Test
    @DisplayName("Integration: Should get all users via HTTP GET")
    void testGetAllUsersIntegration() {
        // Create test users
        userService.createUser("Alice", "alice@example.com");
        userService.createUser("Bob", "bob@example.com");

        ResponseEntity<User[]> response = restTemplate.getForEntity(baseUrl, User[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
    }

    @Test
    @DisplayName("Integration: Should get user by ID via HTTP GET")
    void testGetUserByIdIntegration() {
        User createdUser = userService.createUser("Jane", "jane@example.com");

        ResponseEntity<User> response = restTemplate.getForEntity(
            baseUrl + "/" + createdUser.getId(),
            User.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Jane", response.getBody().getName());
    }

    @Test
    @DisplayName("Integration: Should return 404 for non-existent user")
    void testGetUserByIdNotFoundIntegration() {
        ResponseEntity<User> response = restTemplate.getForEntity(
            baseUrl + "/999",
            User.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // ========== UPDATE Integration Tests ==========

    @Test
    @DisplayName("Integration: Should update user via HTTP PUT")
    void testUpdateUserIntegration() {
        User createdUser = userService.createUser("Old Name", "old@example.com");
        UpdateUserRequest updateRequest = new UpdateUserRequest(
            "New Name",
            "new@example.com"
        );

        HttpEntity<UpdateUserRequest> requestEntity = new HttpEntity<>(updateRequest);
        ResponseEntity<User> response = restTemplate.exchange(
            baseUrl + "/" + createdUser.getId(),
            HttpMethod.PUT,
            requestEntity,
            User.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New Name", response.getBody().getName());
        assertEquals("new@example.com", response.getBody().getEmail());
    }

    @Test
    @DisplayName("Integration: Should return 404 when updating non-existent user")
    void testUpdateUserNotFoundIntegration() {
        UpdateUserRequest updateRequest = new UpdateUserRequest("Name", "email@example.com");
        HttpEntity<UpdateUserRequest> requestEntity = new HttpEntity<>(updateRequest);

        ResponseEntity<User> response = restTemplate.exchange(
            baseUrl + "/999",
            HttpMethod.PUT,
            requestEntity,
            User.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // ========== DELETE Integration Tests ==========

    @Test
    @DisplayName("Integration: Should delete user via HTTP DELETE")
    void testDeleteUserIntegration() {
        User createdUser = userService.createUser("To Delete", "delete@example.com");

        ResponseEntity<Void> response = restTemplate.exchange(
            baseUrl + "/" + createdUser.getId(),
            HttpMethod.DELETE,
            null,
            Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        
        // Verify deletion
        assertFalse(userService.findById(createdUser.getId()).isPresent());
    }

    @Test
    @DisplayName("Integration: Should return 404 when deleting non-existent user")
    void testDeleteUserNotFoundIntegration() {
        ResponseEntity<Void> response = restTemplate.exchange(
            baseUrl + "/999",
            HttpMethod.DELETE,
            null,
            Void.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // ========== COMPLETE CRUD WORKFLOW Integration Test ==========

    @Test
    @DisplayName("Integration: Complete CRUD workflow with real HTTP calls")
    void testCompleteCRUDWorkflowIntegration() {
        // CREATE - POST
        CreateUserRequest createRequest = new CreateUserRequest("Alice", "alice@example.com");
        ResponseEntity<User> createResponse = restTemplate.postForEntity(
            baseUrl,
            createRequest,
            User.class
        );
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        Long userId = createResponse.getBody().getId();
        assertNotNull(userId);

        // READ - GET by ID
        ResponseEntity<User> getResponse = restTemplate.getForEntity(
            baseUrl + "/" + userId,
            User.class
        );
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals("Alice", getResponse.getBody().getName());

        // UPDATE - PUT
        UpdateUserRequest updateRequest = new UpdateUserRequest(
            "Alice Smith",
            "alice.smith@example.com"
        );
        ResponseEntity<User> updateResponse = restTemplate.exchange(
            baseUrl + "/" + userId,
            HttpMethod.PUT,
            new HttpEntity<>(updateRequest),
            User.class
        );
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertEquals("Alice Smith", updateResponse.getBody().getName());

        // DELETE - DELETE
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
            baseUrl + "/" + userId,
            HttpMethod.DELETE,
            null,
            Void.class
        );
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        // VERIFY DELETION - GET should return 404
        ResponseEntity<User> verifyResponse = restTemplate.getForEntity(
            baseUrl + "/" + userId,
            User.class
        );
        assertEquals(HttpStatus.NOT_FOUND, verifyResponse.getStatusCode());
    }

    @Test
    @DisplayName("Integration: Should get user count")
    void testGetUserCountIntegration() {
        userService.createUser("User 1", "user1@example.com");
        userService.createUser("User 2", "user2@example.com");

        ResponseEntity<Integer> response = restTemplate.getForEntity(
            baseUrl + "/count",
            Integer.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody());
    }
}
