package com.movieinventory.service;

public class InvalidMovieTitleException extends RuntimeException {
    InvalidMovieTitleException(String titles) {
        super(String.format("%sdoes not exist", titles));
    }
}
