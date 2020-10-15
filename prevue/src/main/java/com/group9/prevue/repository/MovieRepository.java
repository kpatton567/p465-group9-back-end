package com.group9.prevue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group9.prevue.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

}
