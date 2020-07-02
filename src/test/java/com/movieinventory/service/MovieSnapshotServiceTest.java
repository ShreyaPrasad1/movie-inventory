package com.movieinventory.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieinventory.model.MovieSnapshot;
import com.movieinventory.model.Rating;
import com.movieinventory.repository.MovieSnapshotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MovieSnapshotServiceTest {

    @Mock
    private MovieSnapshotRepository movieSnapshotRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private MovieSnapshotService movieSnapshotService = new MovieSnapshotService(movieSnapshotRepository);


    @Test
    void shouldGetMoviesListFromGivenUrlAndSaveMovieSnapshots() throws JsonProcessingException, ParseException {
        MovieSnapshot movieSnapshot = new MovieSnapshot("Deadpool",
                "2016",
                convertToDate("12 Feb 2016"),
                "Tim Miller",
                Arrays.asList(new Rating("Internet Movie Database", "8.0/10"),
                        new Rating("Rotten Tomatoes", "85%")));

        List<String> movieTitles = Collections.singletonList("Deadpool");
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);

        when(restTemplate.getForObject(any(), eq(String.class)))
                .thenReturn("");
        when(objectMapper.readValue(any(String.class), eq(MovieSnapshot.class))).thenReturn(movieSnapshot);
        movieSnapshotService.createSnapshots(movieTitles);

        verify(movieSnapshotRepository, times(1)).saveAll(captor.capture());
        List<MovieSnapshot> actualCapturedValue = captor.getValue();

        assertEquals(movieSnapshot.getTitle(), actualCapturedValue.get(0).getTitle());
        assertEquals(movieSnapshot.getYear(), actualCapturedValue.get(0).getYear());
        assertEquals(movieSnapshot.getReleased(), actualCapturedValue.get(0).getReleased());
        assertEquals(movieSnapshot.getDirector(), actualCapturedValue.get(0).getDirector());
        assertThat(movieSnapshot.getRatings().get(0)).isEqualToComparingFieldByField(actualCapturedValue.get(0).getRatings().get(0));

    }

    private Date convertToDate(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd MMM yyyy");
        return format.parse(date);
    }
}
