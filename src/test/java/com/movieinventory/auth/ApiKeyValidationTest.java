package com.movieinventory.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApiKeyValidationTest {
    @Autowired
    private ApiKeyValidation apiKeyValidation;

    @Test
    void shouldThrowExceptionWhenApikeyIsNotCorrect() throws AuthenticationException {
        assertThrows(AuthenticationException.class, () -> apiKeyValidation.validate("xyz"));
    }

    @Test
    void shouldNotThrowExceptionWhenApikeyIsCorrect() throws AuthenticationException {
        assertDoesNotThrow(() -> apiKeyValidation.validate("d2ViLWFwcGxpY2F0aW9uLWluLWZsYXNr"));
    }

}
