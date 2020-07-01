package com.movieinventory.domain;

import java.util.Collections;
import java.util.Date;
import java.util.List;


public class MovieSnapshot {

    private String title;
    private String releaseYear;
    private Date releaseDate;
    private String director;
    private List<Rating> ratings;

    public MovieSnapshot(String title, String releaseYear, Date releaseDate, String director, List<Rating> ratings) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.releaseDate = releaseDate;
        this.director = director;
        this.ratings = ratings;
    }

    public MovieSnapshot() {
    }

    private Date convertToDate(String date){
        return null;
    }
}
