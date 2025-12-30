package com.example.tuto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * STEP 5: Testing REST Controllers with MockMvc
 * This demonstrates testing HTTP endpoints with mocked services
 * 
 * @WebMvcTest - Tests only the web layer (controllers)
 * @MockBean - Creates a mock of the service
 */
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setup() {
        testUser = new User(1L, "John Doe", "john@example.com");
    }

    // ========== CREATE Tests ==========

    @Test
    @DisplayName("POST /api/users - Should create user successfully")
    void testCreateUser() throws Exception {
        // Mock the service behavior
        when(userService.createUser("John Doe", "john@example.com"))
            .thenReturn(testUser);

        // Perform POST request and verify
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"email\":\"john@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        // Verify service was called
        verify(userService, times(1)).createUser("John Doe", "john@example.com");
    }

    @Test
    @DisplayName("POST /api/users - Should return 400 for invalid data")
    void testCreateUserInvalidData() throws Exception {
        // Mock service throwing exception
        when(userService.createUser(anyString(), anyString()))
            .thenThrow(new IllegalArgumentException("Invalid email"));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John\",\"email\":\"invalid\"}"))
                .andExpect(status().isBadRequest());
    }

    // ========== READ Tests ==========

    @Test
    @DisplayName("GET /api/users - Should return all users")
    void testGetAllUsers() throws Exception {
        User user1 = new User(1L, "Alice", "alice@example.com");
        User user2 = new User(2L, "Bob", "bob@example.com");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].name").value("Bob"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    @DisplayName("GET /api/users/{id} - Should return user when found")
    void testGetUserById() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(userService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("GET /api/users/{id} - Should return 404 when user not found")
    void testGetUserByIdNotFound() throws Exception {
        when(userService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findById(999L);
    }

    @Test
    @DisplayName("GET /api/users/count - Should return user count")
    void testGetUserCount() throws Exception {
        when(userService.getUserCount()).thenReturn(5);

        mockMvc.perform(get("/api/users/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        verify(userService, times(1)).getUserCount();
    }

    // ========== UPDATE Tests ==========

    @Test
    @DisplayName("PUT /api/users/{id} - Should update user successfully")
    void testUpdateUser() throws Exception {
        User updatedUser = new User(1L, "Jane Doe", "jane@example.com");
        when(userService.updateUser(1L, "Jane Doe", "jane@example.com"))
            .thenReturn(Optional.of(updatedUser));

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Jane Doe\",\"email\":\"jane@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane@example.com"));

        verify(userService, times(1)).updateUser(1L, "Jane Doe", "jane@example.com");
    }

    @Test
    @DisplayName("PUT /api/users/{id} - Should return 404 when user not found")
    void testUpdateUserNotFound() throws Exception {
        when(userService.updateUser(anyLong(), anyString(), anyString()))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Jane\",\"email\":\"jane@example.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/users/{id} - Should return 400 for invalid data")
    void testUpdateUserInvalidData() throws Exception {
        when(userService.updateUser(anyLong(), anyString(), anyString()))
            .thenThrow(new IllegalArgumentException("Invalid email"));

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Jane\",\"email\":\"invalid\"}"))
                .andExpect(status().isBadRequest());
    }

    // ========== DELETE Tests ==========

    @Test
    @DisplayName("DELETE /api/users/{id} - Should delete user successfully")
    void testDeleteUser() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    @DisplayName("DELETE /api/users/{id} - Should return 404 when user not found")
    void testDeleteUserNotFound() throws Exception {
        when(userService.deleteUser(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).deleteUser(999L);
    }

    // ========== COMPLETE CRUD WORKFLOW TEST ==========

    @Test
    @DisplayName("Should handle complete CRUD workflow through REST API")
    void testCompleteCRUDWorkflowViaAPI() throws Exception {
        // CREATE
        User newUser = new User(1L, "Alice", "alice@example.com");
        when(userService.createUser("Alice", "alice@example.com"))
            .thenReturn(newUser);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Alice\",\"email\":\"alice@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        // READ
        when(userService.findById(1L)).thenReturn(Optional.of(newUser));
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice"));

        // UPDATE
        User updatedUser = new User(1L, "Alice Smith", "alice.smith@example.com");
        when(userService.updateUser(1L, "Alice Smith", "alice.smith@example.com"))
            .thenReturn(Optional.of(updatedUser));

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Alice Smith\",\"email\":\"alice.smith@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice Smith"));

        // DELETE
        when(userService.deleteUser(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        // Verify all operations were called
        verify(userService).createUser("Alice", "alice@example.com");
        verify(userService, atLeastOnce()).findById(1L);
        verify(userService).updateUser(1L, "Alice Smith", "alice.smith@example.com");
        verify(userService).deleteUser(1L);
    }
}
