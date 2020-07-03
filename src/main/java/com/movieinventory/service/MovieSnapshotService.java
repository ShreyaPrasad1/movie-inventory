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

    private void titleValidation(String title, Boolean apiResponse) {
        if (!apiResponse) {
            throw new InvalidMovieTitleException(title);
        }
    }

    public void createSnapshots(List<String> movieTitles) {
        String url = "http://www.omdbapi.com/?apikey=b5bece98";

        ArrayList<MovieSnapshot> movieSnapshots = new ArrayList<>();
        ArrayList<String> invalidTitles = new ArrayList<>();
        movieTitles.forEach(title -> {
            String titleUrl = url + "&t=" + title;
            MovieSnapshot movieSnapshot = restTemplate.getForObject(titleUrl, MovieSnapshot.class);
            titleValidation(title, movieSnapshot.getResponse());
            movieSnapshots.add(movieSnapshot);
        });
        movieSnapshotRepository.saveAll(movieSnapshots);
    }

    public List<MovieSnapshot> getMovieSnapshots() {
        return movieSnapshotRepository.findAll();
    }
}
