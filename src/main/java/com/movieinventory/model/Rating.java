package com.movieinventory.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Rating {

    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty("Source")
    private String source;

    @JsonProperty("Value")
    private String value;

    public Rating() {
    }

    public Rating(String source, String value) {
        this.source = source;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getValue() {
        return value;
    }
}
