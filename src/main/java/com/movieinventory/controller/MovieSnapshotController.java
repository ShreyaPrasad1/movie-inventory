package com.movieinventory.controller;

import com.movieinventory.service.MovieSnapshotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieSnapshotController {

    private MovieSnapshotService movieSnapshotService;

    public MovieSnapshotController(MovieSnapshotService movieSnapshotService) {
        this.movieSnapshotService = movieSnapshotService;
    }

    @PostMapping("/movies")
    public ResponseEntity<String> addMovieSnapshots(@RequestBody List<String> titles) {
        movieSnapshotService.createSnapshots(titles);
        return new ResponseEntity<>("Movie Snapshots created", HttpStatus.CREATED);
    }

}
