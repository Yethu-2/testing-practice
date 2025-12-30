package com.example.tuto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * STEP 4: Parameterized Tests
 * Run the same test with different inputs
 */
class ParameterizedTestExamples {

    private final Calculator calculator = new Calculator();

    @ParameterizedTest
    @DisplayName("Should add numbers correctly with multiple inputs")
    @CsvSource({
        "1, 1, 2",
        "2, 3, 5",
        "5, 5, 10",
        "-1, 1, 0",
        "100, 200, 300"
    })
    void testAddWithMultipleInputs(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }

    @ParameterizedTest
    @DisplayName("Should identify even numbers")
    @ValueSource(ints = {2, 4, 6, 8, 10, 100})
    void testEvenNumbers(int number) {
        assertTrue(calculator.isEven(number));
    }

    @ParameterizedTest
    @DisplayName("Should identify odd numbers")
    @ValueSource(ints = {1, 3, 5, 7, 9, 99})
    void testOddNumbers(int number) {
        assertFalse(calculator.isEven(number));
    }

    @ParameterizedTest
    @DisplayName("Should validate email formats")
    @ValueSource(strings = {
        "test@example.com",
        "user@domain.com",
        "admin@company.org"
    })
    void testValidEmails(String email) {
        assertTrue(email.contains("@"));
    }

    @ParameterizedTest
    @DisplayName("Should handle multiplication with different inputs")
    @CsvSource({
        "0, 5, 0",
        "1, 5, 5",
        "2, 3, 6",
        "-1, 5, -5",
        "-2, -3, 6"
    })
    void testMultiplication(int a, int b, int expected) {
        assertEquals(expected, calculator.multiply(a, b));
    }
}
