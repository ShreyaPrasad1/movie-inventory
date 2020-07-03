package com.movieinventory.controller;

import com.movieinventory.auth.ApiKeyValidation;
import com.movieinventory.model.MovieSnapshot;
import com.movieinventory.model.Rating;
import com.movieinventory.service.MovieSnapshotService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieSnapshotController.class)
class MovieSnapshotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieSnapshotService movieSnapshotService;

    @MockBean
    private ApiKeyValidation apiKeyValidation;

    @Test
    void shouldReturnHttpStatus201WhenMovieSnapshotIsSaved() throws Exception {
        Mockito.doNothing().when(apiKeyValidation).validate("randomKey");
        this.mockMvc.perform(post("/movies").contentType(MediaType.APPLICATION_JSON)
                .header("x-api-key", "randomKey")
                .content("[\"Deadpool\"]")).andExpect(status().isCreated())
                .andExpect(content().string(containsString("Movie Snapshots created")));
    }

    @Test
    void shouldReturnMovieSnapshotsWhenGetAPIIsCalled() throws Exception {
        when(movieSnapshotService.getMovieSnapshots()).thenReturn(getAllMovies());
        this.mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].Released").value("2016-02-11T18:30:00.000+00:00"))
                .andExpect(jsonPath("$[0].Title").value("Deadpool"))
                .andExpect(jsonPath("$[0].Director").value("Tim Miller"))
                .andExpect(jsonPath("$[0].Ratings[0].Source").value("Internet Movie Database"))
                .andExpect(jsonPath("$[1].Released").value("2016-02-11T18:30:00.000+00:00"))
                .andExpect(jsonPath("$[1].Title").value("Batman"))
                .andExpect(jsonPath("$[1].Director").value("Tim Miller"))
                .andExpect(jsonPath("$[1].Ratings[0].Source").value("Internet Movie Database"));
    }

    private List<MovieSnapshot> getAllMovies() throws ParseException {
        MovieSnapshot movieSnapshotOne = new MovieSnapshot("Deadpool",
                "2016",
                convertToDate("12 Feb 2016"),
                "Tim Miller",
                Arrays.asList(new Rating("Internet Movie Database", "8.0/10"),
                        new Rating("Rotten Tomatoes", "85%")),
                true);

        MovieSnapshot movieSnapshotTwo = new MovieSnapshot("Batman",
                "2016",
                convertToDate("12 Feb 2016"),
                "Tim Miller",
                Arrays.asList(new Rating("Internet Movie Database", "8.0/10"),
                        new Rating("Rotten Tomatoes", "85%")),
                true);

        return Arrays.asList(movieSnapshotOne, movieSnapshotTwo);
    }

    private Date convertToDate(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd MMM yyyy");
        return format.parse(date);
    }
}