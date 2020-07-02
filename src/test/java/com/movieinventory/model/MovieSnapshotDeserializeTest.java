package com.movieinventory.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieSnapshotDeserializeTest {

    @Test
    void shouldDeserializeToMovieSnapshotObject() throws ParseException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        String json = "{\n" +
                "  \"Title\": \"Deadpool\",\n" +
                "  \"Year\": \"2016\",\n" +
                "  \"Rated\": \"R\",\n" +
                "  \"Released\": \"12 Feb 2016\",\n" +
                "  \"Runtime\": \"108 min\",\n" +
                "  \"Genre\": \"Action, Adventure, Comedy, Sci-Fi\",\n" +
                "  \"Director\": \"Tim Miller\",\n" +
                "  \"Writer\": \"Rhett Reese, Paul Wernick\",\n" +
                "  \"Actors\": \"Ryan Reynolds, Karan Soni, Ed Skrein, Michael Benyaer\",\n" +
                "  \"Plot\": \"A wisecracking mercenary gets experimented on and becomes immortal but ugly, and sets out to track down the man who ruined his looks.\",\n" +
                "  \"Language\": \"English\",\n" +
                "  \"Country\": \"USA\",\n" +
                "  \"Awards\": \"Nominated for 2 Golden Globes. Another 28 wins & 75 nominations.\",\n" +
                "  \"Poster\": \"https://m.media-amazon.com/images/M/MV5BYzE5MjY1ZDgtMTkyNC00MTMyLThhMjAtZGI5OTE1NzFlZGJjXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\",\n" +
                "  \"Ratings\": [\n" +
                "    {\n" +
                "      \"Source\": \"Internet Movie Database\",\n" +
                "      \"Value\": \"8.0/10\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"Source\": \"Rotten Tomatoes\",\n" +
                "      \"Value\": \"85%\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        MovieSnapshot actualMovieSnapshot = objectMapper.readValue(json, MovieSnapshot.class);
        MovieSnapshot expectedMovieSnapshot = new MovieSnapshot("Deadpool", "2016",
                convertToDate("12 Feb 2016"),
                "Tim Miller",
                Arrays.asList(new Rating("Internet Movie Database", "8.0/10"),
                        new Rating("Rotten Tomatoes", "85%")));

        assertEquals(expectedMovieSnapshot.getTitle(), actualMovieSnapshot.getTitle());
        assertEquals(expectedMovieSnapshot.getYear(), actualMovieSnapshot.getYear());
        assertEquals(expectedMovieSnapshot.getReleased(), actualMovieSnapshot.getReleased());
        assertEquals(expectedMovieSnapshot.getDirector(), actualMovieSnapshot.getDirector());
        assertThat(actualMovieSnapshot.getRatings().get(0)).isEqualToComparingFieldByField(actualMovieSnapshot.getRatings().get(0));
        assertThat(actualMovieSnapshot.getRatings().get(1)).isEqualToComparingFieldByField(actualMovieSnapshot.getRatings().get(1));
    }

    private Date convertToDate(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd MMM yyyy");
        return format.parse(date);
    }
}
