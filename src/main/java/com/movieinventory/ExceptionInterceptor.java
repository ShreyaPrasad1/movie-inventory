package com.movieinventory;

import com.movieinventory.auth.AuthenticationException;
import com.movieinventory.service.InvalidMovieTitleException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InvalidMovieTitleException.class)
    public ResponseEntity<Object> handleInvalidMovieException(InvalidMovieTitleException exception, WebRequest request) {
        return handleExceptionInternal(exception,
                new ApiResponse(HttpStatus.OK, exception.getMessage()),
                new HttpHeaders(), HttpStatus.OK, request);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception, WebRequest webRequest) {
        return handleExceptionInternal(exception,
                new ApiResponse(HttpStatus.UNAUTHORIZED, exception.getMessage()),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, webRequest);
    }

}
