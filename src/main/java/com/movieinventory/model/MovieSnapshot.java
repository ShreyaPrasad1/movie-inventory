package com.movieinventory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
public class MovieSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    private Date released;

    @JsonProperty("Director")
    private String director;

    @OneToMany
    @Cascade(CascadeType.ALL)
    @JsonProperty("Ratings")
    private List<Rating> ratings;

    public MovieSnapshot(String title, String year, Date released, String director, List<Rating> ratings) throws ParseException {
        this.title = title;
        this.year = year;
        this.released = released;
        this.director = director;
        this.ratings = ratings;
    }

    public MovieSnapshot() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public Date getReleased() {
        return released;
    }

    public String getDirector() {
        return director;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    @JsonProperty("Released")
    private void convertToDate(String dateString) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd MMM yyyy");
        this.released = format.parse(dateString);
    }
}
