package com.movieinventory;

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
    public ResponseEntity<Object> handleException(InvalidMovieTitleException exception, WebRequest request) {
        return handleExceptionInternal(exception, "Movie Title does not exist",
                new HttpHeaders(), HttpStatus.OK, request);
    }
}
