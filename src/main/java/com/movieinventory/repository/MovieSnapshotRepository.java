package com.movieinventory.repository;

import com.movieinventory.model.MovieSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieSnapshotRepository extends JpaRepository<MovieSnapshot, Long> {

}
