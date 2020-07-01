package com.movieinventory.service;

import com.movieinventory.domain.MovieSnapshot;
import com.movieinventory.repository.MovieSnapshotRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service

public class MovieSnapshotService {

    private MovieSnapshotRepository movieSnapshotRepository;

    public MovieSnapshotService(MovieSnapshotRepository movieSnapshotRepository) {
        this.movieSnapshotRepository = movieSnapshotRepository;
    }

    public void createSnapshots(List<String> movieTitles) {

        String url = "http://www.omdbapi.com/?apikey=b5bece98";

        ArrayList<MovieSnapshot> movieSnapshots = new ArrayList<>();

        movieTitles.stream().forEach(title -> {
            String titleUrl = url + "&t=" + title;
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<MovieSnapshot> movieSnapshot = restTemplate.getForEntity(titleUrl, MovieSnapshot.class);
            movieSnapshots.add(movieSnapshot.getBody());
        });

        movieSnapshotRepository.saveAll(movieSnapshots);

        System.out.println("records saved successfully");
    }
}
