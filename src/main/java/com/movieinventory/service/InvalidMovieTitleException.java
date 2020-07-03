package com.movieinventory.service;

public class InvalidMovieTitleException extends RuntimeException {
    public InvalidMovieTitleException(String title) {
        super(String.format("Snapshots not created. %s does not exist", title));
    }
}
