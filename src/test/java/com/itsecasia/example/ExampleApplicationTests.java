package com.itsecasia.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Example Test Suite")
class ExampleApplicationTests {

    @Test
    @DisplayName("Example sucessfully load context")
    void contextLoads() {
        assertTrue(true);
    }

}
