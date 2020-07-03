package com.movieinventory.controller;

import com.movieinventory.ApiResponse;
import com.movieinventory.auth.ApiKeyValidation;
import com.movieinventory.model.MovieSnapshot;
import com.movieinventory.service.MovieSnapshotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
public class MovieSnapshotController {

    private MovieSnapshotService movieSnapshotService;
    private ApiKeyValidation apiKeyValidation;

    public MovieSnapshotController(MovieSnapshotService movieSnapshotService, ApiKeyValidation apiKeyValidation) {
        this.movieSnapshotService = movieSnapshotService;
        this.apiKeyValidation = apiKeyValidation;
    }

    @PostMapping("/movies")
    public ResponseEntity<ApiResponse> addMovieSnapshots(@RequestBody List<String> titles, @RequestHeader("x-api-key") String apiKeyHeader) throws AuthenticationException, org.apache.tomcat.websocket.AuthenticationException {
        apiKeyValidation.validate(apiKeyHeader);
        movieSnapshotService.createSnapshots(titles);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED,
                "Movie Snapshots created"), HttpStatus.CREATED);
    }

    @GetMapping("/movies")
    public List<MovieSnapshot> getMovieSnapshot(){
        return movieSnapshotService.getMovieSnapshots();
    }

}
