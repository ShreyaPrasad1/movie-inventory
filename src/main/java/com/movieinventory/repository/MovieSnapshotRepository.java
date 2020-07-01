package com.movieinventory.repository;

import com.movieinventory.domain.MovieSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MovieSnapshotRepository extends JpaRepository<MovieSnapshot, Long> {



}
