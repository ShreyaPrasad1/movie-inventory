package com.movieinventory.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class MovieSnapshotControllerTest {

    @Autowired
    private MockMvc mockMvc ;

    @BeforeEach
    void setUp() {

    }
    @Test
    void shouldReturnHttpStatus201WhenMovieSnapshotIsSaved() throws Exception {
        this.mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON)
                .content("[\"Deadpool\"]")).andExpect(status().isCreated())
                .andExpect(content().string(containsString("Movie Snapshots created")));
    }
}