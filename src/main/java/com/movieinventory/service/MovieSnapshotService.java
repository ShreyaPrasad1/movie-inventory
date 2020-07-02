package com.movieinventory.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieinventory.model.MovieSnapshot;
import com.movieinventory.repository.MovieSnapshotRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieSnapshotService {

    private MovieSnapshotRepository movieSnapshotRepository;
    private ObjectMapper objectMapper;

    MovieSnapshotService(MovieSnapshotRepository movieSnapshotRepository) {
        this.movieSnapshotRepository = movieSnapshotRepository;
        this.objectMapper = new ObjectMapper().
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public void createSnapshots(List<String> movieTitles) {

        String url = "http://www.omdbapi.com/?apikey=b5bece98";

        ArrayList<MovieSnapshot> movieSnapshots = new ArrayList<>();

        movieTitles.forEach(title -> {
            String titleUrl = url + "&t=" + title;
            RestTemplate restTemplate = new RestTemplate();
            String movieSnapshotJson = restTemplate.getForObject(titleUrl, String.class);
            try {
                assert movieSnapshotJson != null;
                MovieSnapshot movieSnapshot = objectMapper.readValue(movieSnapshotJson, MovieSnapshot.class);
                movieSnapshots.add(movieSnapshot);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        movieSnapshotRepository.saveAll(movieSnapshots);
    }
}
