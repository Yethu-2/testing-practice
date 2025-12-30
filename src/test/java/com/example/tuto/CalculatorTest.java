package com.example.tuto;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * STEP 1: Basic JUnit Testing
 * This test demonstrates fundamental JUnit annotations and assertions
 */
class CalculatorTest {

    private Calculator calculator;

    // Runs once before all tests in this class
    @BeforeAll
    static void setupAll() {
        System.out.println("🚀 Starting Calculator Tests");
    }

    // Runs before each test method
    @BeforeEach
    void setup() {
        calculator = new Calculator();
        System.out.println("✨ New Calculator instance created");
    }

    // Runs after each test method
    @AfterEach
    void tearDown() {
        System.out.println("✅ Test completed\n");
    }

    // Runs once after all tests
    @AfterAll
    static void tearDownAll() {
        System.out.println("🎉 All Calculator Tests Completed");
    }

    // BASIC ASSERTION EXAMPLES

    @Test
    @DisplayName("Should add two positive numbers")
    void testAddPositiveNumbers() {
        // Arrange (Given) - Set up test data
        int a = 5;
        int b = 3;

        // Act (When) - Execute the method being tested
        int result = calculator.add(a, b);

        // Assert (Then) - Verify the result
        assertEquals(8, result, "5 + 3 should equal 8");
    }

    @Test
    @DisplayName("Should subtract two numbers")
    void testSubtract() {
        int result = calculator.subtract(10, 4);
        assertEquals(6, result);
    }

    @Test
    @DisplayName("Should multiply two numbers")
    void testMultiply() {
        int result = calculator.multiply(4, 5);
        assertEquals(20, result);
    }

    @Test
    @DisplayName("Should divide two numbers")
    void testDivide() {
        double result = calculator.divide(10, 2);
        assertEquals(5.0, result);
    }

    @Test
    @DisplayName("Should throw exception when dividing by zero")
    void testDivideByZero() {
        // Testing that an exception is thrown
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.divide(10, 0)
        );
        
        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    @Test
    @DisplayName("Should return true for even numbers")
    void testIsEven() {
        assertTrue(calculator.isEven(4), "4 is even");
        assertTrue(calculator.isEven(0), "0 is even");
        assertFalse(calculator.isEven(3), "3 is not even");
    }

    @Test
    @DisplayName("Should handle negative numbers in addition")
    void testAddWithNegatives() {
        assertEquals(-2, calculator.add(-5, 3));
        assertEquals(-8, calculator.add(-5, -3));
    }

    // Grouping related tests
    @Nested
    @DisplayName("Tests for multiplication edge cases")
    class MultiplicationTests {
        
        @Test
        void testMultiplyByZero() {
            assertEquals(0, calculator.multiply(5, 0));
        }

        @Test
        void testMultiplyNegativeNumbers() {
            assertEquals(15, calculator.multiply(-3, -5));
        }
    }

    // This test is disabled - useful when you're still working on something
    @Test
    @Disabled("Not implemented yet")
    void testFutureFeature() {
        // Will be skipped
    }
}
