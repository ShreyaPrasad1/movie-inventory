package com.movieinventory.service;

import com.movieinventory.domain.MovieSnapshot;
import com.movieinventory.domain.Rating;
import com.movieinventory.repository.MovieSnapshotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MovieSnapshotServiceTest {


    @Mock
    private MovieSnapshotRepository movieSnapshotRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MovieSnapshotService movieSnapshotService = new MovieSnapshotService(movieSnapshotRepository);


    @Test
    void shouldGetMoviesListFromGivenUrlAndSaveMovieSnapshots() {

        MovieSnapshot movieSnapshot = new MovieSnapshot("Deadpool",
                "2028",
                new Date(), "M John",
                Collections.singletonList(new Rating("", "")));
        List<String> movieTitles = Arrays.asList("Deadpool");
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        when(restTemplate.getForEntity(any(), eq(MovieSnapshot.class)))
                .thenReturn(new ResponseEntity<MovieSnapshot>(movieSnapshot, HttpStatus.OK));

        movieSnapshotService.createSnapshots(movieTitles);

        verify(movieSnapshotRepository, times(1)).saveAll(captor.capture());

        assertEquals(captor.getValue().get(0), movieSnapshot);

    }
}