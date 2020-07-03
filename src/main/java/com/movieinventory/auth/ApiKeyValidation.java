package com.movieinventory.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyValidation {
    @Value("${auth.x-api-key}")
    private String xApiKey;

    public boolean validate(String receivedApiKey) throws AuthenticationException {
        if (!xApiKey.equals(receivedApiKey)) {
            throw new AuthenticationException();
        }
        return true;
    }
}
