package com.movieinventory.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MovieSnapshotTest {
    @Test
    void shouldDeserializeToMovieSnapshotObject() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        String json = "{\n" +
                "  \"Title\": \"XYZ\",\n" +
                "  \"Year\": \"2000\",\n" +
                "  \"Rated\": \"N/A\",\n" +
                "  \"Released\": \"N/A\",\n" +
                "  \"Runtime\": \"92 min\",\n" +
                "  \"Genre\": \"N/A\",\n" +
                "  \"Director\": \"John B. Root\",\n" +
                "  \"Writer\": \"N/A\",\n" +
                "  \"Actors\": \"Titof, K. Sandra, Ovidie, Sebastian Barrio\",\n" +
                "  \"Plot\": \"N/A\",\n" +
                "  \"Language\": \"French\",\n" +
                "  \"Country\": \"France\",\n" +
                "  \"Awards\": \"N/A\",\n" +
                "  \"Poster\": \"N/A\",\n" +
                "  \"Ratings\": [\n" +
                "    {\n" +
                "      \"Source\": \"Internet Movie Database\",\n" +
                "      \"Value\": \"6.8/10\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"Metascore\": \"N/A\",\n" +
                "  \"imdbRating\": \"6.8\",\n" +
                "  \"imdbVotes\": \"28\",\n" +
                "  \"imdbID\": \"tt0326432\",\n" +
                "  \"Type\": \"movie\",\n" +
                "  \"DVD\": \"N/A\",\n" +
                "  \"BoxOffice\": \"N/A\",\n" +
                "  \"Production\": \"N/A\",\n" +
                "  \"Website\": \"N/A\",\n" +
                "  \"Response\": \"True\"\n" +
                "}";
        MovieSnapshot actualMovieSnapshot = objectMapper.readValue(json, MovieSnapshot.class);
        MovieSnapshot expectedMovieSnapshot = new MovieSnapshot("XYZ", "2000",
                null,
                "John B. Root",
                Arrays.asList(new Rating("Internet Movie Database", "6.8/10")), true);

        assertEquals(expectedMovieSnapshot.getTitle(), actualMovieSnapshot.getTitle());
        assertEquals(expectedMovieSnapshot.getYear(), actualMovieSnapshot.getYear());
        assertNull(actualMovieSnapshot.getReleased());
        assertEquals(expectedMovieSnapshot.getDirector(), actualMovieSnapshot.getDirector());
        assertThat(actualMovieSnapshot.getRatings().get(0)).isEqualToComparingFieldByField(actualMovieSnapshot.getRatings().get(0));
    }

}