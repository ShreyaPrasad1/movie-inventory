package com.movieinventory.service;

import com.movieinventory.model.MovieSnapshot;
import com.movieinventory.model.Rating;
import com.movieinventory.repository.MovieSnapshotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieSnapshotServiceTest {

    @Mock
    private MovieSnapshotRepository movieSnapshotRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MovieSnapshotService movieSnapshotService = new MovieSnapshotService(movieSnapshotRepository, restTemplate);


    @Test
    void shouldGetMoviesListFromGivenUrlAndSaveMovieSnapshots() {
        MovieSnapshot movieSnapshot = new MovieSnapshot("Deadpool",
                "2016",
                convertToDate("12 Feb 2016"),
                "Tim Miller",
                Arrays.asList(new Rating("Internet Movie Database", "8.0/10"),
                        new Rating("Rotten Tomatoes", "85%")),
                true);

        List<String> movieTitles = Collections.singletonList("Deadpool");
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);

        when(restTemplate.getForObject("http://www.omdbapi.com/?apikey=b5bece98&t=Deadpool", MovieSnapshot.class))
                .thenReturn(movieSnapshot);

        movieSnapshotService.createSnapshots(movieTitles);

        verify(movieSnapshotRepository, times(1)).saveAll(captor.capture());
        List<MovieSnapshot> actualCapturedValue = captor.getValue();

        assertEquals(movieSnapshot.getTitle(), actualCapturedValue.get(0).getTitle());
        assertEquals(movieSnapshot.getYear(), actualCapturedValue.get(0).getYear());
        assertEquals(movieSnapshot.getReleased(), actualCapturedValue.get(0).getReleased());
        assertEquals(movieSnapshot.getDirector(), actualCapturedValue.get(0).getDirector());
        assertThat(movieSnapshot.getRatings().get(0)).isEqualToComparingFieldByField(actualCapturedValue.get(0).getRatings().get(0));

    }

    @Test
    void shouldThrowErrorWhenAPIDoesNotReturnMovieSnapshot() {

        MovieSnapshot movieSnapshot = new MovieSnapshot(null, null, null, null, null, false);

        List<String> movieTitles = Collections.singletonList("Deadpool");
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);

        when(restTemplate.getForObject("http://www.omdbapi.com/?apikey=b5bece98&t=Deadpool", MovieSnapshot.class))
                .thenReturn(movieSnapshot);


        Exception exception = Assertions.assertThrows(InvalidMovieTitleException.class, () -> movieSnapshotService.createSnapshots(movieTitles));

        String expectedMessage = "Snapshots not created. Deadpool does not exist";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void shouldReturnListOfMovies() {
        when(movieSnapshotRepository.findAll()).thenReturn(getAllMovies());

        List<MovieSnapshot> movieSnapshots = movieSnapshotService.getMovieSnapshots();

        assertEquals(2, movieSnapshots.size());
        List<MovieSnapshot> expectedMovieSnapshots = getAllMovies();
        assertEquals(movieSnapshots.get(0).getTitle(), expectedMovieSnapshots.get(0).getTitle());
        assertEquals(movieSnapshots.get(1).getTitle(), expectedMovieSnapshots.get(1).getTitle());
    }


    private List<MovieSnapshot> getAllMovies() {
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

    private Date convertToDate(String date) {
        DateFormat format = new SimpleDateFormat("dd MMM yyyy");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
