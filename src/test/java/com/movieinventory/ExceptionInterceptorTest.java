package com.movieinventory;

import com.movieinventory.auth.AuthenticationException;
import com.movieinventory.service.InvalidMovieTitleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ExceptionInterceptor.class)
class ExceptionInterceptorTest {

    @Autowired
    private ExceptionInterceptor exceptionInterceptor;

    @Mock
    private WebRequest webRequest;

    @Test
    void shouldSendInvalidTitleExceptionMsgWhenInvalidTitleExceptionIsThrown() {
        ResponseEntity<Object> responseEntity = exceptionInterceptor.handleInvalidMovieException(
                new InvalidMovieTitleException("deadpol"), webRequest);

        ApiResponse actualApiResponse = (ApiResponse) responseEntity.getBody();
        ApiResponse expectedApiResponse = new ApiResponse(HttpStatus.OK, "Snapshots not created. deadpol does not exist");
        assertEquals(expectedApiResponse.getMessage(), actualApiResponse.getMessage());
        assertEquals(expectedApiResponse.getStatus(), actualApiResponse.getStatus());
    }

    @Test
    void shouldSendAuthenticationExceptionMsgWhenAuthenticationExceptionIsThrown() {
        ResponseEntity<Object> responseEntity = exceptionInterceptor.handleAuthenticationException(
                new AuthenticationException(), webRequest);

        ApiResponse actualApiResponse = (ApiResponse) responseEntity.getBody();
        ApiResponse expectedApiResponse = new ApiResponse(HttpStatus.UNAUTHORIZED, "Invalid x-api-key");
        assertEquals(expectedApiResponse.getMessage(), actualApiResponse.getMessage());
        assertEquals(expectedApiResponse.getStatus(), actualApiResponse.getStatus());
    }
}