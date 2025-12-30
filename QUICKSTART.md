# 🚀 Quick Start Guide - JUnit & CRUD Testing

## ⚡ 5-Minute Quick Start

### 1. Run All Tests
```bash
./mvnw test
```
**Expected**: 77 tests pass ✅

### 2. Run Specific Test (Start Here!)
```bash
./mvnw test -Dtest=CalculatorTest
```
Open [CalculatorTest.java](src/test/java/com/example/tuto/CalculatorTest.java) and read through it!

### 3. Start the Application
```bash
./mvnw spring-boot:run
```

### 4. Test the REST API
```bash
# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@test.com"}'

# Get all users
curl http://localhost:8080/api/users
```

---

## 📖 Learning Path (Step by Step)

### **Day 1: JUnit Basics** (30 minutes)
1. Read: [JUNIT_LEARNING_GUIDE.md](JUNIT_LEARNING_GUIDE.md)
2. Open: [CalculatorTest.java](src/test/java/com/example/tuto/CalculatorTest.java)
3. Run: `./mvnw test -Dtest=CalculatorTest`
4. **Practice**: Add a `power(base, exponent)` method and write tests

### **Day 2: Service Testing** (45 minutes)
1. Open: [UserServiceTest.java](src/test/java/com/example/tuto/UserServiceTest.java)
2. Run: `./mvnw test -Dtest=UserServiceTest`
3. Study: How each CRUD operation is tested
4. **Practice**: Add a `findByEmail()` method and write tests

### **Day 3: REST API Testing** (60 minutes)
1. Read: [CRUD_SUMMARY.md](CRUD_SUMMARY.md)
2. Open: [UserControllerTest.java](src/test/java/com/example/tuto/UserControllerTest.java)
3. Run: `./mvnw test -Dtest=UserControllerTest`
4. **Practice**: Add a new endpoint and test it

### **Day 4: Integration Testing** (45 minutes)
1. Read: [ARCHITECTURE.md](ARCHITECTURE.md)
2. Open: [UserControllerIntegrationTest.java](src/test/java/com/example/tuto/UserControllerIntegrationTest.java)
3. Run: `./mvnw test -Dtest=UserControllerIntegrationTest`
4. **Practice**: Test a complete user lifecycle

### **Day 5: Advanced Techniques** (60 minutes)
1. Open: [ParameterizedTestExamples.java](src/test/java/com/example/tuto/ParameterizedTestExamples.java)
2. Study: Parameterized tests
3. **Practice**: Create a Product CRUD with full test coverage

---

## 📁 Files to Explore (In Order)

| # | File | Purpose | Lines |
|---|------|---------|-------|
| 1 | [CalculatorTest.java](src/test/java/com/example/tuto/CalculatorTest.java) | Learn JUnit basics | ~150 |
| 2 | [UserServiceTest.java](src/test/java/com/example/tuto/UserServiceTest.java) | CRUD unit testing | ~200 |
| 3 | [UserControllerTest.java](src/test/java/com/example/tuto/UserControllerTest.java) | REST API testing | ~230 |
| 4 | [UserControllerIntegrationTest.java](src/test/java/com/example/tuto/UserControllerIntegrationTest.java) | Full integration | ~220 |
| 5 | [ParameterizedTestExamples.java](src/test/java/com/example/tuto/ParameterizedTestExamples.java) | Advanced testing | ~90 |

---

## 🎯 What You'll Learn

### JUnit Fundamentals ✅
- [x] Test annotations (@Test, @BeforeEach, etc.)
- [x] Assertions (assertEquals, assertTrue, etc.)
- [x] Test lifecycle
- [x] Nested tests
- [x] Parameterized tests

### Spring Boot Testing ✅
- [x] @SpringBootTest
- [x] @WebMvcTest
- [x] MockMvc
- [x] TestRestTemplate
- [x] @MockBean

### CRUD Operations ✅
- [x] Create (POST)
- [x] Read (GET)
- [x] Update (PUT)
- [x] Delete (DELETE)

### Testing Levels ✅
- [x] Unit tests (service layer)
- [x] Controller tests (with mocks)
- [x] Integration tests (full stack)

### Best Practices ✅
- [x] Arrange-Act-Assert pattern
- [x] Test naming conventions
- [x] Error case testing
- [x] Complete workflow testing

---

## 🛠️ Common Commands

### Testing
```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=UserServiceTest

# Run with verbose output
./mvnw test -X

# Clean and test
./mvnw clean test

# Skip tests (not recommended for learning!)
./mvnw install -DskipTests
```

### Running the Application
```bash
# Start application
./mvnw spring-boot:run

# Build JAR
./mvnw package

# Run JAR
java -jar target/tuto-0.0.1-SNAPSHOT.jar
```

### Useful Maven Commands
```bash
# Check dependencies
./mvnw dependency:tree

# Update dependencies
./mvnw versions:display-dependency-updates

# Show project info
./mvnw help:effective-pom
```

---

## 🧪 Testing with curl

### Complete CRUD Workflow
```bash
# 1. CREATE
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com"}'
# Response: {"id":1,"name":"John Doe","email":"john@example.com"}

# 2. READ - Get all
curl http://localhost:8080/api/users
# Response: [{"id":1,"name":"John Doe","email":"john@example.com"}]

# 3. READ - Get by ID
curl http://localhost:8080/api/users/1
# Response: {"id":1,"name":"John Doe","email":"john@example.com"}

# 4. UPDATE
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"John Smith","email":"john.smith@example.com"}'
# Response: {"id":1,"name":"John Smith","email":"john.smith@example.com"}

# 5. DELETE
curl -X DELETE http://localhost:8080/api/users/1
# Response: (empty, status 204)

# 6. VERIFY DELETE
curl http://localhost:8080/api/users/1
# Response: (empty, status 404)

# Get count
curl http://localhost:8080/api/users/count
# Response: 0
```

---

## 💡 Tips for Learning

1. **Start Simple**: Begin with CalculatorTest, it's the easiest
2. **Read Comments**: All test files have detailed comments
3. **Run Tests Often**: See them pass, then break them to understand
4. **One Concept at a Time**: Don't try to learn everything at once
5. **Practice Writing Tests**: Best way to learn is by doing
6. **Read Error Messages**: They're helpful for learning
7. **Use Debugger**: Set breakpoints in tests to understand flow
8. **Ask Questions**: Check the learning guides when stuck

---

## 📚 Documentation Files

| File | Description |
|------|-------------|
| [JUNIT_LEARNING_GUIDE.md](JUNIT_LEARNING_GUIDE.md) | Complete JUnit tutorial |
| [CRUD_SUMMARY.md](CRUD_SUMMARY.md) | CRUD operations explained |
| [ARCHITECTURE.md](ARCHITECTURE.md) | System architecture diagrams |
| [QUICKSTART.md](QUICKSTART.md) | This file! |

---

## 🎓 Test Statistics

```
Total Tests: 77 ✅

By Type:
├── Basic JUnit Tests:        10
├── Parameterized Tests:      25
├── Service Unit Tests:       17
├── Service Integration:       2
├── Controller Tests:         12
├── Integration Tests:        10
└── Context Tests:             1

By Focus:
├── CRUD Operations:          39 tests
├── JUnit Features:           35 tests
└── Spring Integration:        3 tests

Execution Time:
├── Unit Tests:           < 1 second
├── Controller Tests:     ~1.5 seconds
└── Integration Tests:    ~2.5 seconds
    Total:                ~5 seconds
```

---

## ❓ Troubleshooting

### Tests won't run
```bash
# Clean and rebuild
./mvnw clean test
```

### Port already in use
```bash
# Stop the running application
# Press Ctrl+C in the terminal where it's running
```

### Maven not found
```bash
# Use the wrapper (no Maven installation needed)
./mvnw test
```

### Can't connect to localhost
```bash
# Make sure the application is running
./mvnw spring-boot:run
```

---

## 🎉 Next Steps

After mastering these tests:

1. **Add New Features**
   - Implement Product CRUD
   - Add user validation rules
   - Implement search functionality

2. **Explore Advanced Topics**
   - Add database (H2, PostgreSQL)
   - Implement authentication
   - Add input validation
   - Error handling with @ControllerAdvice

3. **Improve Test Coverage**
   - Add edge case tests
   - Test concurrent operations
   - Performance testing
   - Security testing

4. **Learn More Tools**
   - Postman (API testing)
   - Swagger (API documentation)
   - Actuator (monitoring)
   - Docker (containerization)

---

**Happy Testing! 🚀**
