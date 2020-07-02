package com.movieinventory.service;

import com.movieinventory.model.MovieSnapshot;
import com.movieinventory.repository.MovieSnapshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieSnapshotService {

    private MovieSnapshotRepository movieSnapshotRepository;
    private RestTemplate restTemplate;

    MovieSnapshotService(MovieSnapshotRepository movieSnapshotRepository, RestTemplate restTemplate) {
        this.movieSnapshotRepository = movieSnapshotRepository;
        this.restTemplate = restTemplate;
    }

    public void createSnapshots(List<String> movieTitles) throws InvalidMovieTitleException {

        String url = "http://www.omdbapi.com/?apikey=b5bece98";

        ArrayList<MovieSnapshot> movieSnapshots = new ArrayList<>();

        ArrayList<String> invalidTitles = new ArrayList<>();
        movieTitles.forEach(title -> {
            String titleUrl = url + "&t=" + title;
            MovieSnapshot movieSnapshot = restTemplate.getForObject(titleUrl, MovieSnapshot.class);
            if (!movieSnapshot.getResponse()) {
                invalidTitles.add(title);
            }
            movieSnapshots.add(movieSnapshot);
        });

        if (invalidTitles.size() != 0) {
            String invalidMovieTitles = movieTitles.stream().reduce("", (titles, title) -> titles += title + ", ");
            throw new InvalidMovieTitleException(invalidMovieTitles);
        }
        movieSnapshotRepository.saveAll(movieSnapshots);
    }
}
