package com.movieinventory.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApiKeyValidationTest {
    @Autowired
    private ApiKeyValidation apiKeyValidation;

    @Test
    void shouldThrowExceptionWhenApikeyIsNotCorrect() {
        assertThrows(AuthenticationException.class, () -> apiKeyValidation.validate("xyz"));
    }

    @Test
    void shouldNotThrowExceptionWhenApikeyIsCorrect() {
        assertDoesNotThrow(() -> apiKeyValidation.validate("d2ViLWFwcGxpY2F0aW9uLWluLWZsYXNr"));
    }

}
