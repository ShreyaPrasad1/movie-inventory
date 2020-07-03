package com.movieinventory.service;

public class InvalidMovieTitleException extends RuntimeException {
    public InvalidMovieTitleException(String titles) {
        super(String.format("Snapshots not created. %sdoes not exist", titles));
    }
}
