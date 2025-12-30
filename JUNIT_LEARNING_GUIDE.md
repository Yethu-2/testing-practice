# 🎓 JUnit Testing Learning Guide

## What I've Created For You

I've set up a complete learning environment with progressive examples. Here's your learning path:

## 📚 Learning Steps

### **STEP 1: Basic JUnit Tests** ✅ Created
**File**: `CalculatorTest.java`

**What you'll learn**:
- `@Test` - Mark methods as test cases
- `@BeforeEach` / `@AfterEach` - Setup and cleanup
- `@BeforeAll` / `@AfterAll` - One-time setup/cleanup
- `@DisplayName` - Give tests readable names
- `@Disabled` - Skip tests temporarily
- `@Nested` - Group related tests

**Key Assertions**:
```java
assertEquals(expected, actual)      // Check equality
assertTrue(condition)               // Check if true
assertFalse(condition)              // Check if false
assertNotNull(object)               // Check not null
assertThrows(Exception.class, ...)  // Check exception thrown
```

**Try it**: Run `CalculatorTest.java` and watch the tests pass!

---

### **STEP 2: Testing Service Classes** ✅ Created
**File**: `UserServiceTest.java`

**What you'll learn**:
- Testing business logic
- Arrange-Act-Assert pattern (Given-When-Then)
- Testing edge cases and error conditions
- Testing with `Optional` return types
- Multiple assertions in one test

**Real-world scenarios**:
- Creating objects
- Validation logic
- Finding by ID
- Deleting records
- Handling non-existent data

**Try it**: Run `UserServiceTest.java` to see comprehensive service testing!

---

### **STEP 3: Integration Testing with Spring** ✅ Created
**File**: `UserServiceIntegrationTest.java`

**What you'll learn**:
- `@SpringBootTest` - Load full Spring context
- `@Autowired` - Dependency injection in tests
- Testing with real Spring beans
- Difference between unit and integration tests

**Understanding**:
- **Unit Test**: Tests class in isolation (fast)
- **Integration Test**: Tests with Spring context (slower but realistic)

---

### **STEP 4: Parameterized Tests** ✅ Created
**File**: `ParameterizedTestExamples.java`

**What you'll learn**:
- `@ParameterizedTest` - Run same test with different data
- `@ValueSource` - Single parameter values
- `@CsvSource` - Multiple parameters (CSV format)
- Testing multiple scenarios efficiently

**Benefits**:
- Less code duplication
- Easy to add new test cases
- Clear test data visualization

---

### **STEP 5: Complete CRUD Operations** ✅ Created
**Files**: 
- `UserController.java` - REST API with full CRUD
- `UserControllerTest.java` - Unit tests with MockMvc
- `UserControllerIntegrationTest.java` - Full integration tests

**What you'll learn**:
- **CRUD Operations**:
  - **C**reate - POST /api/users
  - **R**ead - GET /api/users, GET /api/users/{id}
  - **U**pdate - PUT /api/users/{id}
  - **D**elete - DELETE /api/users/{id}
- `@WebMvcTest` - Test web layer only
- `MockMvc` - Simulate HTTP requests
- `@MockBean` - Mock service dependencies
- `TestRestTemplate` - Real HTTP calls
- Testing HTTP status codes (200, 201, 404, 400)
- Testing JSON responses
- Verifying mock interactions

**Real-world REST API testing**:
- Testing controllers without starting full server (MockMvc)
- Full integration testing with running server (TestRestTemplate)
- Mocking dependencies to isolate controller logic
- Testing request/response serialization

---

## 🚀 How to Run Tests

### Option 1: Run All Tests
```bash
./mvnw test
```

### Option 2: Run Specific Test Class
```bash
./mvnw test -Dtest=CalculatorTest
./mvnw test -Dtest=UserServiceTest
./mvnw test -Dtest=UserControllerTest
./mvnw test -Dtest=UserControllerIntegrationTest
```

### Option 3: In VS Code
- Open any test file
- Click the ▶️ icon next to test methods
- Or right-click → "Run Test"

### Option 4: Start the Application
```bash
./mvnw spring-boot:run
```
Then test the REST API at: `http://localhost:8080/api/users`

### H2 In-Memory DB Quick Guide
- JDBC URL: `jdbc:h2:mem:tuto`  | User: `sa` | Password: (empty)
- H2 console: `http://localhost:8080/h2-console` (enable after app start)
- Driver: `org.h2.Driver`
- In-memory DB persists while the app runs; restarts give a fresh DB.
- Tests use this in-memory DB automatically via Spring Boot defaults.

**How to open console:**
1) Start app: `./mvnw spring-boot:run`
2) Navigate to `/h2-console`
3) Set JDBC URL to `jdbc:h2:mem:tuto`, user `sa`, no password
4) Connect and browse tables; run SQL (e.g., `SELECT * FROM USER;`).

---

## 📝 Practice Exercises

### **Beginner Level**
1. ✏️ Add a new method to `Calculator` (e.g., `power(base, exponent)`)
2. ✏️ Write tests for your new method
3. ✏️ Add a test that checks for negative numbers

### **Intermediate Level**
4. ✏️ Add an `updateUser()` method to `UserService`
5. ✏️ Write comprehensive tests for it
6. ✏️ Add a method to find users by email

### **Advanced Level**
7. ✏️ Create a new `Product` and `ProductService` class
8. ✏️ Write full test coverage
9. ✏️ Add parameterized tests for product validation
10. ✏️ Create a `ProductController` with CRUD operations
11. ✏️ Write both MockMvc and Integration tests for the controller

---

## 🎯 Understanding CRUD Operations

### What is CRUD?
CRUD stands for the four basic operations in persistent storage:

**C**reate - Add new records (POST)  
**R**ead - Retrieve existing records (GET)  
**U**pdate - Modify existing records (PUT/PATCH)  
**D**elete - Remove records (DELETE)

### REST API Endpoints
```
POST   /api/users          → Create new user
GET    /api/users          → Get all users
GET    /api/users/{id}     → Get specific user
PUT    /api/users/{id}     → Update user
DELETE /api/users/{id}     → Delete user
```

### HTTP Status Codes
- `200 OK` - Successful GET/PUT
- `201 Created` - Successful POST
- `204 No Content` - Successful DELETE
- `400 Bad Request` - Invalid data
- `404 Not Found` - Resource doesn't exist

---

## 🧪 Testing Types Explained

### 1. **Unit Tests** (Fast, Isolated)
- Test individual methods/classes
- No Spring context
- Mock all dependencies
- Example: `UserServiceTest.java`

### 2. **Integration Tests with Mocks** (Medium speed)
- Test controller layer
- Mock service layer
- Use `@WebMvcTest` and `MockMvc`
- Example: `UserControllerTest.java`

### 3. **Full Integration Tests** (Slower, Realistic)
- Test entire application stack
- Real HTTP calls to running server
- Use `@SpringBootTest` and `TestRestTemplate`
- Example: `UserControllerIntegrationTest.java`

---

## 🎯 JUnit Best Practices

### 1. **Test Naming**
```java
// ❌ Bad
@Test void test1() { }

// ✅ Good
@Test
@DisplayName("Should return user when ID exists")
void shouldReturnUserWhenIdExists() { }
```

### 2. **Arrange-Act-Assert Pattern**
```java
@Test
void testExample() {
    // Arrange (Given) - Setup
    UserService service = new UserService();
    
    // Act (When) - Execute
    User user = service.createUser("John", "john@test.com");
    
    // Assert (Then) - Verify
    assertNotNull(user);
}
```

// MockMvc for REST API testing
mockMvc.perform(get("/api/users/1"))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.name").value("John"))

// Verify mock interactions (Mockito)
verify(mockService, times(1)).methodName(args)

### 3. **One Concept Per Test**
```java
// ❌ Bad - Testing multiple things
@Test void testEverything() {
    // tests creation, deletion, update, etc.
}

// ✅ Good - One test per concept
@Test void shouldCreateUser() { }
@Test void shouldDeleteUser() { }
@Test void shouldUpdateUser() { }
```

### 4. **Test Edge Cases**
- Empty strings
- Null values
- Zero and negative numbers
- Boundary values
- Exception scenarios

---

## 🔍 Common Assertions Quick Reference

```java
// Equality
assertEquals(expected, actual)
assertNotEquals(expected, actual)

// Boolean
assertTrue(condition)
assertFalse(condition)

// Null checks
assertNull(object)
assertNotNull(object)

// Exceptions
assertThrows(Exception.class, () -> { code })

// Collections
assertIterableEquals(expected, actual)

// Timeouts
assertTimeout(Duration.ofSeconds(1), () -> { code })

// Multiple assertions
assertAll(
    () -> assertEquals(expected1, actual1),
    () -> assertEquals(expected2, actual2)
)Start the application** (`./mvnw spring-boot:run`)
3. **Test the REST API** using a tool like Postman or curl:
   ```bash
   # Create user
   curl -X POST http://localhost:8080/api/users \
     -H "Content-Type: application/json" \
     -d '{"name":"John","email":"john@test.com"}'
   
   # Get all users
   curl http://localhost:8080/api/users
   
   # Get user by ID
   curl http://localhost:8080/api/users/1
   
   # Update user
   curl -X PUT http://localhost:8080/api/users/1 \
     -H "Content-Type: application/json" \
     -d '{"name":"John Updated","email":"john.new@test.com"}'
   
   # Delete user
   curl -X DELETE http://localhost:8080/api/users/1
   ```
4. **Modify the tests** to understand how they work
5. **Add new test cases** for practice
6. **Create your own CRUD API** and write comprehensive tests

## 📖 Next Steps

1. **Run the existing tests** to see them in action
2. **Modify the tests** to understand how they work
3. **Add new test cases** for practice
4. **Create your own classes** and write tests
5. **Explore Mockito** for mocking dependencies (Step 5)

---

## 🆘 Need Help?

- Check test output for detailed error messages
- Read the error stack traces carefully
- Use `System.out.println()` to debug tests
- Review the test files I created as reference

Happy Testing! 🎉
